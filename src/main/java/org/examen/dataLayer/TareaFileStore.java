package org.examen.dataLayer;

import jakarta.xml.bind.JAXBContext;
import org.examen.domainLayer.Tarea;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.stream.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
public class TareaFileStore implements IFileStore<Tarea> {

    private final File xmlFile;

    public TareaFileStore(File xmlFile) {
        this.xmlFile = xmlFile;
        ensureFile();
    }
    private void ensureFile() {
        try {
            File parent = xmlFile.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
                writeAll(new ArrayList<>());
            }
        } catch (Exception ignored) {}
    }
    @Override
    public void writeAll(List<Tarea> data) {
        try (FileOutputStream out = new FileOutputStream(xmlFile)) {
            JAXBContext ctx = JAXBContext.newInstance(Tarea.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);

            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            XMLStreamWriter xw = xof.createXMLStreamWriter(out, "UTF-8");

            xw.writeStartDocument("UTF-8", "1.0");
            xw.writeStartElement("tareas");

            if (data != null) {
                for (Tarea t : data) {
                    m.marshal(t, xw);
                }
            }

            xw.writeEndElement();
            xw.writeEndDocument();
            xw.flush();
            xw.close();
        } catch (Exception ex) {
            System.err.println("[WARN] Error escribiendo " + xmlFile);
            ex.printStackTrace();
        }
    }
    @Override
    public List<Tarea> readAll() {
        List<Tarea> out = new ArrayList<>();
        if (xmlFile.length() == 0) return out;

        try {
            JAXBContext ctx = JAXBContext.newInstance(Tarea.class);
            Unmarshaller u = ctx.createUnmarshaller();


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            System.out.println("tamaño del xml"+ xmlFile.length());



            NodeList tareaNodes = doc.getElementsByTagName("tarea");

            for (int i = 0; i < tareaNodes.getLength(); i++) {
                Node tareaNode = tareaNodes.item(i);
                if (tareaNode.getNodeType() == Node.ELEMENT_NODE){
                    try {
                        Tarea pro = (Tarea) u.unmarshal(tareaNode);
                        out.add(pro);
                    } catch (Exception e) {
                        System.err.println("[WARN] Error deserializando tarea " + i + ": " + e.getMessage());
                    }
                }
            }

        } catch (Exception ex) {
            System.err.println("[WARN] Error leyendo " + xmlFile + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return out;
    }
}

