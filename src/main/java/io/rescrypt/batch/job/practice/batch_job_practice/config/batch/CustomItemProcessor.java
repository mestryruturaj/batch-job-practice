package io.rescrypt.batch.job.practice.batch_job_practice.config.batch;

import io.rescrypt.batch.job.practice.batch_job_practice.model.Product;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Product, Product> {
    /**
     * Process the provided item, returning a potentially modified or new item for
     * continued processing. If the returned result is {@code null}, it is assumed that
     * processing of the item should not continue.
     * <p>
     * A {@code null} item will never reach this method because the only possible sources
     * are:
     * <ul>
     * <li>an {@link ItemReader} (which indicates no more items)</li>
     * <li>a previous {@link ItemProcessor} in a composite processor (which indicates a
     * filtered item)</li>
     * </ul>
     *
     * @param item to be processed, never {@code null}.
     * @return potentially modified or new item for continued processing, {@code null} if
     * processing of the provided item should not continue.
     * @throws Exception thrown if exception occurs during processing.
     */
    @Override
    public Product process(Product item) throws Exception {
        Double discountPercentage = item.getDiscount();
        Double actualPrice = item.getPrice();
        Double discountedPrice = actualPrice - (actualPrice * discountPercentage / 100);
        item.setDiscountedPrice(discountedPrice);
        return item;
    }
}
