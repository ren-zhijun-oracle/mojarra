/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2014 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.sun.faces.test.servlet30.composite;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.junit.*;
import static org.junit.Assert.assertTrue;

public class Issue2195IT {

    private String webUrl;
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }

    @Test
    public void testActionListener1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/actionListener/actionListener1.xhtml");
        HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementById("form:cc_action:button");
        page = (HtmlPage) submit.click();
        assertTrue(page.asXml().contains("PROCESSACTION CALLED"));
    }

    @Test
    public void testAjax1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/ajax/ajax1.xhtml");
        HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementById("form:cc_ajax:button");
        page = (HtmlPage) submit.click();
        webClient.waitForBackgroundJavaScript(60000);
        assertTrue(page.asXml().contains("PROCESSAJAXBEHAVIOR CALLED"));
    }

    @Test
    public void testEvent1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/event/event1.xhtml");
        HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementById("form:button");
        page = (HtmlPage) submit.click();
        assertTrue(page.asXml().contains("LISTENER CALLED"));
    }

    @Test
    public void testValueChangeListener1() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/valueChangeListener/valueChangeListener1.xhtml");
        HtmlTextInput input = (HtmlTextInput) page.getElementById("form:cc_value:input");
        input.setValueAttribute("Foo");
        HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementById("form:button");
        page = (HtmlPage) submit.click();
        assertTrue(page.asXml().contains("VALUECHANGE CALLED"));
    }
}
