package com.nameof.raftkv;

import cn.hutool.core.lang.Tuple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogFormatterTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testLog_SetCommand_ReturnsFormattedString() {
        String result = LogFormatter.log("set", "key", "1");
        Assert.assertEquals("set key 1", result);
    }

    @Test
    public void testLog_DeleteCommand_ReturnsFormattedString() {
        String result = LogFormatter.log("delete", "key", null);
        Assert.assertEquals("delete key", result);
    }

    @Test
    public void testLog_ClearCommand_ReturnsFormattedString() {
        String result = LogFormatter.log("clear", null, null);
        Assert.assertEquals("clear", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLog_InvalidCommand_ThrowsException() {
        LogFormatter.log("invalid", "key", "1");
    }

    @Test
    public void testParseLogEntry_ValidSetCommand_ReturnsTuple() {
        Tuple result = LogFormatter.parseLogEntry("set key 1");
        Assert.assertEquals("set", result.get(0));
        Assert.assertEquals("key", result.get(1));
        Assert.assertEquals("1", result.get(2));
    }

    @Test
    public void testParseLogEntry_ValidDeleteCommand_ReturnsTuple() {
        Tuple result = LogFormatter.parseLogEntry("delete key");
        Assert.assertEquals("delete", result.get(0));
        Assert.assertEquals("key", result.get(1));
        Assert.assertNull(result.get(2));
    }

    @Test
    public void testParseLogEntry_ValidClearCommand_ReturnsTuple() {
        Tuple result = LogFormatter.parseLogEntry("clear");
        Assert.assertEquals("clear", result.get(0));
        Assert.assertNull(result.get(1));
        Assert.assertNull(result.get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseLogEntry_InvalidFormat_ThrowsException() {
        LogFormatter.parseLogEntry("invalid format");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseLogEntry_SetCommandWithInvalidFormat_ThrowsException() {
        LogFormatter.parseLogEntry("set key");
    }
}