package com.berryworks.edireader.util.sax;

import com.berryworks.edireader.DefaultXMLTags;
import com.berryworks.edireader.EDIAttributes;
import com.berryworks.edireader.XMLTags;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import static org.junit.Assert.assertEquals;

public class EDIReaderSAXAdapterTest {

    private MyAdapater adapter;

    @Test
    public void basics() throws SAXException {
        XMLTags xmlTags = new DefaultXMLTags();
        adapter = new MyAdapater(xmlTags);

        EDIAttributes attributes = new EDIAttributes();
        attributes.addCDATA("A", "a");

        adapter.startElement("", "interchange", "interchange", attributes);
        assertEquals("Interchange.", adapter.getTrace());

        adapter.startElement("", "group", "group", attributes);
        adapter.startElement("", "transaction", "transaction", attributes);
        assertEquals("Interchange.Group.Transaction.", adapter.getTrace());

        adapter.startElement("", "segment", "segment", attributes);
        adapter.startElement("", "element", "element", attributes);
        assertEquals("Interchange.Group.Transaction.FirstSegment.Element.", adapter.getTrace());
    }

    class MyAdapater extends EDIReaderSAXAdapter {

        private StringBuilder builder = new StringBuilder();

        public MyAdapater(XMLTags xmlTags) {
            super(xmlTags);
        }

        @Override
        protected void beginInterchange(int charCount, int segmentCharCount, Attributes attributes) {
            builder.append("Interchange.");
        }

        @Override
        protected void beginExplicitGroup(int charCount, int segmentCharCount, Attributes attributes) {
            builder.append("Group.");
        }

        @Override
        protected void beginDocument(int charCount, int segmentCharCount, Attributes attributes) {
            builder.append("Transaction.");
        }

        @Override
        protected void beginFirstSegment(Attributes atts) {
            builder.append("FirstSegment.");
        }

        @Override
        protected void beginSegmentElement(Attributes atts) {
            builder.append("Element.");
            ;
        }

        public String getTrace() {
            return builder.toString();
        }
    }
}
