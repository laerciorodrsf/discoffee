package com.laerciorodrsf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.laerciorodrsf.listeners.TwitterListener;

public class TwitterListenerTest {

    private final TwitterListener listener = new TwitterListener();

    @Test
    void shouldConvertTwitterLink() {
        String message = "https://twitter.com/user/status/123456";
        List<String> result = listener.convertTwitterUrl(message);

        assertEquals("https://fxtwitter.com/user/status/123456", result.get(0));
    }

    @Test 
    void shouldConvertTwitterLinkWithAdditionalText() {
        String message = "Take a look at this post: https://twitter.com/user/status/123456";
        List<String> result = listener.convertTwitterUrl(message);

        assertEquals( "https://fxtwitter.com/user/status/123456", result.get(0) );
    }

    @Test 
    void shouldConvertXLInk() {
        String message = "https://x.com/user/status/123456";
        List<String> result = listener.convertTwitterUrl(message);

        assertEquals( "https://fxtwitter.com/user/status/123456", result.get(0) );
    }

    @Test 
    void shouldConvertWithWww() {
        String message = "https://www.twitter.com/user/status/123456";
        List<String> result = listener.convertTwitterUrl(message);

        assertEquals( "https://www.fxtwitter.com/user/status/123456", result.get(0) );
    }

    @Test
    void shouldNotConvertWhenMessageDoesNotMatch() {
        String message = "Hello, how are you?";
        List<String> result = listener.convertTwitterUrl(message);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldConvertMultipleLinks() {
        String message = """
                Link 1: https://twitter.com/user/status/123
                Link 2: https://x.com/user/status/456
                """;
        List<String> result = listener.convertTwitterUrl(message);

        assertEquals(2, result.size());
        assertEquals( "https://fxtwitter.com/user/status/123", result.get(0) );
        assertEquals( "https://fxtwitter.com/user/status/456", result.get(1) );
    }

}
