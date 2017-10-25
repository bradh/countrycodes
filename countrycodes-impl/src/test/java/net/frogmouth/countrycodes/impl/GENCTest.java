/*
 * The MIT License
 *
 * Copyright 2017 Brad Hards
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.frogmouth.countrycodes.impl;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Base GENC Tests.
 */
public class GENCTest {

    public GENCTest() {
    }

    @Test
    public void SimpleConstructor() {
        GENC genc = new GENC();
        assertNotNull(genc);
    }

    @Test
    public void NotInitialised() {
        GENC genc = new GENC();
        assertFalse(genc.isInitialised());
    }

    @Test
    public void Initialisation() {
        GENC genc = new GENC();
        genc.initialise();
        assertTrue(genc.isInitialised());
    }

    @Test
    public void Authority() {
        GENC genc = new GENC();
        genc.initialise();
        assertEquals("GENC", genc.getAuthority());
    }

    @Test(expected = IllegalStateException.class)
    public void AuthorityNoInit() {
        GENC genc = new GENC();
        genc.getAuthority();
    }

    @Test
    public void PromulgationDate() {
        GENC genc = new GENC();
        genc.initialise();
        assertEquals(LocalDate.of(2017, 6, 30), genc.getPromulgationDate());
    }

    @Test(expected = IllegalStateException.class)
    public void PromulgationDateNoInit() {
        GENC genc = new GENC();
        genc.getPromulgationDate();
    }

    @Test
    public void Baseline() {
        GENC genc = new GENC();
        genc.initialise();
        assertEquals("3-7", genc.getBaseline());
    }

    @Test(expected = IllegalStateException.class)
    public void BaselineNoInit() {
        GENC genc = new GENC();
        genc.getBaseline();
    }

    @Test
    public void lookupNameShortURN_twoChar() {
        GENC genc = new GENC();
        genc.initialise();
        String name = genc.lookupName("ge:GENC:2:3-7:AU");
        assertEquals("AUSTRALIA", name);
    }

    @Test
    public void lookupNameShortURN_threeChar() {
        GENC genc = new GENC();
        genc.initialise();
        String name = genc.lookupName("ge:GENC:3:3-7:AUS");
        assertEquals("AUSTRALIA", name);
    }

    @Test
    public void lookupNameShortURN_twoCharAR() {
        GENC genc = new GENC();
        genc.initialise();
        String name = genc.lookupName("ge:GENC:2:3-7:ARG");
        assertEquals("ARGENTINA", name);
    }

    @Test
    public void lookupNameShortURN_threeCharARG() {
        GENC genc = new GENC();
        genc.initialise();
        String name = genc.lookupName("ge:GENC:2:3-7:AR");
        assertEquals("ARGENTINA", name);
    }

    /*
    <genc-cmn:char3Code>ARG</genc-cmn:char3Code><genc-cmn:char3CodeURISet><genc-cmn:codespaceURL>http://api.nsgreg.nga.mil/geo-political/GENC/3/3-7</genc-cmn:codespaceURL><genc-cmn:codespaceURN>urn:us:gov:dod:nga:def:geo-political:GENC:3:3-7</genc-cmn:codespaceURN><genc-cmn:codespaceURNBased>geo-political:GENC:3:3-7</genc-cmn:codespaceURNBased><genc-cmn:codespaceURNBasedShort>ge:GENC:3:3-7</genc-cmn:codespaceURNBasedShort></genc-cmn:char3CodeURISet><genc-cmn:char2Code>AR</genc-cmn:char2Code><genc-cmn:char2CodeURISet><genc-cmn:codespaceURL>http://api.nsgreg.nga.mil/geo-political/GENC/2/3-7</genc-cmn:codespaceURL><genc-cmn:codespaceURN>urn:us:gov:dod:nga:def:geo-political:GENC:2:3-7</genc-cmn:codespaceURN><genc-cmn:codespaceURNBased>geo-political:GENC:2:3-7</genc-cmn:codespaceURNBased><genc-cmn:codespaceURNBasedShort>ge:GENC:2:3-7</genc-cmn:codespaceURNBasedShort></genc-cmn:char2CodeURISet><genc-cmn:numericCode>032</genc-cmn:numericCode><genc-cmn:numericCodeURISet><genc-cmn:codespaceURL>http://api.nsgreg.nga.mil/geo-political/GENC/n/3-7</genc-cmn:codespaceURL><genc-cmn:codespaceURN>urn:us:gov:dod:nga:def:geo-political:GENC:n:3-7</genc-cmn:codespaceURN><genc-cmn:codespaceURNBased>geo-political:GENC:n:3-7</genc-cmn:codespaceURNBased><genc-cmn:codespaceURNBasedShort>ge:GENC:n:3-7</genc-cmn:codespaceURNBasedShort></genc-cmn:numericCodeURISet></genc:encoding><genc:name>ARGENTINA</genc:name>
    
     */
}
