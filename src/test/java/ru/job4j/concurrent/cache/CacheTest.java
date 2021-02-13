package ru.job4j.concurrent.cache;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAdd3Objects() throws InterruptedException {
        Cache cache = new Cache();
        Thread thr1 = new Thread(
                () -> {
                    Base model1 = new Base(1, 1);
                    model1.setName("name1");
                    cache.add(model1);
                }
        );
        Thread thr2 = new Thread(
                () -> {
                    Base model2 = new Base(2, 1);
                    model2.setName("name2");
                    cache.add(model2);
                }
        );
        Thread thr3 = new Thread(
                () -> {
                    Base model3 = new Base(3, 1);
                    model3.setName("name3");
                    cache.add(model3);
                }
        );
        thr1.start();
        thr2.start();
        thr3.start();
        thr1.join();
        thr2.join();
        thr3.join();
        assertThat(cache.getContent().size(), is(3));
    }

    @Test
    public void whenAddTheSameObject() throws InterruptedException {
        Cache cache = new Cache();
        Thread thr1 = new Thread(
                () -> {
                    Base model1 = new Base(1, 1);
                    model1.setName("name1");
                    cache.add(model1);
                }
        );
        Thread thr2 = new Thread(
                () -> {
                    Base model2 = new Base(1, 2);
                    model2.setName("name2");
                    cache.add(model2);
                }
        );
        thr1.start();
        thr2.start();
        thr1.join();
        thr2.join();
        assertThat(cache.getContent().size(), is(1));
    }

    @Test
    public void whenRemove3Objects() throws InterruptedException {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(1, 1);
        Base model3 = new Base(1, 1);
        cache.add(model1);
        cache.add(model2);
        cache.add(model3);
        Thread thr1 = new Thread(() -> cache.delete(model1));
        Thread thr2 = new Thread(() -> cache.delete(model2));
        Thread thr3 = new Thread(() -> cache.delete(model3));
        thr1.start();
        thr2.start();
        thr3.start();
        thr1.join();
        thr2.join();
        thr3.join();
        assertThat(cache.getContent().size(), is(0));
    }

    @Test
    public void whenUpdateWrong() throws InterruptedException {
        Base model = new Base(1, 1);
        Cache cache = new Cache();
        model.setName("name");
        cache.add(model);
        Thread thr1 = new Thread(
                () -> {
                    Base updModel = new Base(1, 1);
                    updModel.setName("name1");
                    cache.update(updModel);
                }
        );
        Thread thr2 = new Thread(
                () -> {
                    try {
                        Base updModel = new Base(1, 1);
                        updModel.setName("name2");
                        cache.update(updModel);
                    } catch (OptimisticException e) {
                        assertEquals("Versions are not equal", e.getMessage());
                    }
                }
        );
        thr1.start();
        thr1.join();
        thr2.start();
        thr2.join();
    }

    @Test
    public void whenUpdateOk() throws InterruptedException {
        Base model = new Base(1, 1);
        Cache cache = new Cache();
        model.setName("name");
        cache.add(model);
        Thread thr1 = new Thread(
                () -> {
                    Base updModel = new Base(1, 1);
                    updModel.setName("name1");
                    cache.update(updModel);
                }
        );
        Thread thr2 = new Thread(
                () -> {
                    Base updModel = new Base(1, 2);
                    updModel.setName("name2");
                    cache.update(updModel);
                }
        );
        thr1.start();
        thr1.join();
        thr2.start();
        thr2.join();
        assertThat(cache.getContent().iterator().next().getName(), is("name2"));
    }
}