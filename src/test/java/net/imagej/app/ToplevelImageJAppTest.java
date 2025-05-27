/*
 * #%L
 * ImageJ2 software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2024 ImageJ2 developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imagej.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import net.imagej.ImageJ;
import net.imagej.patcher.LegacyInjector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scijava.app.App;

/**
 * Tests {@link ToplevelImageJApp}.
 * 
 * @author Curtis Rueden
 */
public class ToplevelImageJAppTest {

	private ImageJ ij;

	@Before
	public void setUp() {
        // Initialize LegacyInjector before creating ImageJ
        LegacyInjector.preinit();
        
        // Create ImageJ instance
        ij = new ImageJ();
        ij.context().inject(this);
        
        // Ensure we have the correct app instance
        App app = ij.app().getApp();
        if (!(app instanceof ToplevelImageJApp)) {
            throw new RuntimeException("Expected ToplevelImageJApp, got " + app.getClass().getName());
        }
    }

	@After
	public void tearDown() {
		ij.getContext().dispose();
	}

	@Test
	public void testApp() {
		// Get the app instance
		final App app = ij.app().getApp();
		assertSame(ToplevelImageJApp.class, app.getClass());
		assertEquals("net.imagej", app.getGroupId());
		assertEquals("imagej", app.getArtifactId());
		final String version = app.getVersion();
		assertTrue(version.contains("/1."));
	}

}
