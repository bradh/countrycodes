/*
 * The MIT License
 *
 * Copyright 2017 bradh.
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

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import net.frogmouth.countrycodes.generated.GENCStandardBaseline;
import net.frogmouth.countrycodes.generated.GeopoliticalEntityEntryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Country code lookup for GENC.
 *
 * This might be better as a singleton.
 */
public class GENC {

    private static final Logger LOG = LoggerFactory.getLogger(GENC.class);

    private GENCStandardBaseline baseline;

    boolean isInitialised() {
        return baseline != null;
    }

    public void initialise() {
        // TODO: do initialisation - this should take a version eventually
        try (InputStream is = getClass().getResourceAsStream("/GENC Standard Ed3.0 Update 7.xml")) {
            unmarshal(is);
        } catch (JAXBException ex) {
            LOG.warn("Error parsing input. Set log to DEBUG for more information.");
        } catch (IOException ex) {
            LOG.warn("Error reading input. Set log to DEBUG for more information.");
        }
    }

    private void unmarshal(final InputStream inputStream) throws JAXBException {
        baseline = (GENCStandardBaseline) getUnmarshaller().unmarshal(inputStream);
    }

    private Unmarshaller getUnmarshaller() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(GENCStandardBaseline.class);
        return jc.createUnmarshaller();
    }

    public String lookupName(String string) {
        return lookupEntry(string).getName();
    }

    private GeopoliticalEntityEntryType lookupEntry(String string) {
        verifyIsInitialised();
        // TODO: improve parsing
        String code = string.substring(string.lastIndexOf(':') + 1);
        if (code.length() == 3) {
            return findEntryByCodeAlpha3(code);
        } else {
            return findEntryByCodeAlpha2(code);
        }
    }

    // TODO: maybe pull up
    public String getAuthority() {
        verifyIsInitialised();
        return baseline.getAuthority();
    }

    // TODO: maybe pull up
    public LocalDate getPromulgationDate() {
        verifyIsInitialised();
        return LocalDate.of(baseline.getPromulgationDate().getYear(),
                baseline.getPromulgationDate().getMonth(),
                baseline.getPromulgationDate().getDay());
    }

    private void verifyIsInitialised() {
        if (!isInitialised()) {
            throw new IllegalStateException(this.getClass().getName() + " is not initialized");
        }
    }

    // TODO: maybe pull up
    public String getBaseline() {
        verifyIsInitialised();
        return baseline.getBaseline();
    }

    private GeopoliticalEntityEntryType findEntryByCodeAlpha2(String code) {
        for (GeopoliticalEntityEntryType entry : baseline.getGeopoliticalEntityEntry()) {
            if (code.equals(entry.getEncoding().getChar2Code())) {
                return entry;
            }
        }
        return null;
    }

    private GeopoliticalEntityEntryType findEntryByCodeAlpha3(String code) {
        for (GeopoliticalEntityEntryType entry : baseline.getGeopoliticalEntityEntry()) {
            if (code.equals(entry.getEncoding().getChar3Code())) {
                return entry;
            }
        }
        return null;
    }
}
