package lawnmower;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LawnMowerBatchConfiguration {

    @Bean
    @StepScope
    public FlatFileItemReader<List<String>> reader() {
        return new FlatFileItemReaderBuilder<List<String>>()
                .name("lawnMowerItemReader")
                .resource(new FileSystemResource("src/main/java/resources/input.txt"))
                .lineMapper((line, lineNumber) -> Arrays.asList(line.split(" ")))
                .strict(false)
                .build();
    }

    @Bean
    public ItemProcessor<List<String>, List<LawnMower>> processor() {
        return new LawnMowerProcessor();
    }

    @Bean
    public ItemWriter<List<LawnMower>> itemWriter() {
        return mowers -> mowers.forEach(mowerList -> {
            System.out.println("Resulting mowers: ");
            for (LawnMower mower : mowerList) {
                System.out.println(mower.getPosition());
            }
        });
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("LawnMowerStep",jobRepository).
                <List<String>, List<LawnMower>>chunk(1,transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("mowerJob", jobRepository)
                .flow(step)
                .end()
                .build();
    }
}


