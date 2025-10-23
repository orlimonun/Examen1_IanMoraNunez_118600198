package org.examen.dataLayer;
import jakarta.xml.bind.JAXBContext;
import org.examen.domainLayer.Proyecto;
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

public class    ProyectoFileStore implements IFileStore<Proyecto> {

    private final File xmlFile;

    public ProyectoFileStore(File xmlFile) {
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
    public void writeAll(List<Proyecto> data) {
        try (FileOutputStream out = new FileOutputStream(xmlFile)) {
            JAXBContext ctx = JAXBContext.newInstance(Proyecto.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);

            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            XMLStreamWriter xw = xof.createXMLStreamWriter(out, "UTF-8");

            xw.writeStartDocument("UTF-8", "1.0");
            xw.writeStartElement("proyectos");

            if (data != null) {
                for (Proyecto pro : data) {
                    m.marshal(pro, xw);
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
    public List<Proyecto> readAll() {
        List<Proyecto> out = new ArrayList<>();
        if (xmlFile.length() == 0) return out;

        try {
            JAXBContext ctx = JAXBContext.newInstance(Proyecto.class);
            Unmarshaller u = ctx.createUnmarshaller();


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            System.out.println("tamaño del xml"+ xmlFile.length());



            NodeList proyectoNodes = doc.getElementsByTagName("proyecto");

            for (int i = 0; i < proyectoNodes.getLength(); i++) {
                Node proyectoNode = proyectoNodes.item(i);
                if (proyectoNode.getNodeType() == Node.ELEMENT_NODE){
                    try {
                        Proyecto pro = (Proyecto) u.unmarshal(proyectoNode);
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

