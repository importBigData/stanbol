package org.apache.stanbol.reasoners.it.online;

import org.apache.stanbol.commons.testing.stanbol.StanbolTestBase;
import org.junit.Test;

/**
 * All these tests use the online FOAF ontology
 * 
 * Supported accept headers are:
 * - text/html
 * - text/n3
 * - application/rdf+xml
 * - application/turtle
 * 
 * @author mac
 *
 */
public class ReasonersOnlineTest extends StanbolTestBase{
    
//    private final String NS_rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
//    private final String NS_rdfs="http://www.w3.org/2000/01/rdf-schema#";
    private final String FOAF = "http://xmlns.com/foaf/0.1/";
    
    /**
     * homepage
     * @throws Exception
     */
    @Test
    public void testHomepage() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners")
                .withHeader("Accept", "text/html")
        )
        .assertStatus(200)
        .assertContentType("text/html")
        .assertContentContains(
            "Administrators can enable, disable and deploy reasoning services using t")
        .assertContentRegexp(
            "stylesheet.*stanbol.css",
            "<title.*[Ss]tanbol");
    }

    /**
     * RDFS + CLASSIFY + text/html
     * 
     * @throws Exception
     */
    @Test
    public void testServiceOnlineRdfsClassifyFoafHtml() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners/rdfs/classify","url",FOAF)
                .withHeader("Accept", "text/html")
        )
        .assertStatus(200)
        .assertContentType("text/html")
        .assertContentContains(
            "http://xmlns.com/foaf/0.1/family_name",
            "http://xmlns.com/foaf/0.1/depicts",
            "http://xmlns.com/foaf/0.1/gender",
            "http://purl.org/dc/elements/1.1/title");
    }
    
    /**
     * OWL + CLASSIFY + text/html
     * 
     * @throws Exception
     */
    @Test
    public void testServiceOnlineOwlClassifyFoafHtml() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners/owl/classify","url",FOAF)
                .withHeader("Accept", "text/html")
        )
        .assertStatus(200)
        .assertContentType("text/html")
        .assertContentContains(
            "http://xmlns.com/foaf/0.1/family_name",
            "http://xmlns.com/foaf/0.1/depicts",
            "http://xmlns.com/foaf/0.1/gender",
            "http://purl.org/dc/elements/1.1/title");
    }
    
    /**
     * OWL2 + CLASSIFY + text/html
     * 
     * @throws Exception
     */
    @Test
    public void testServiceOnlineOwl2ClassifyFoafHtml() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners/owl2/classify","url",FOAF)
                .withHeader("Accept", "text/html")
        )
        .assertStatus(200)
        .assertContentType("text/html")
         .assertContentRegexp(
             "&lt;http://purl.org/dc/terms/Agent&gt;",
             "&lt;http://xmlns.com/foaf/0.1/Agent&gt;",
             "owl:Thing"
                 );
    }

    /**
     * RDFS + ENRICH + text/n3
     * 
     * @throws Exception
     */
    @Test
    public void testServiceOnlineRdfsClassifyFoafN3() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners/rdfs/classify","url",FOAF)
                .withHeader("Accept", "text/n3")
        )
        .assertStatus(200)
        .assertContentType("text/n3")
        .assertContentContains(
            "http://xmlns.com/foaf/0.1/family_name",
            "http://xmlns.com/foaf/0.1/depicts",
            "http://xmlns.com/foaf/0.1/gender",
            "http://purl.org/dc/elements/1.1/title");
    }
    
    /**
     * RDFS + ENRICH + application/rdf+xml
     * 
     * @throws Exception
     */
    @Test
    public void testServiceOnlineRdfsEnrichFoafRDFXML() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners/rdfs/enrich","url",FOAF)
                .withHeader("Accept", "application/rdf+xml")
        )
        .assertStatus(200)
        .assertContentType("application/rdf+xml")
        .assertContentContains(
            "rdf:RDF",
            "rdf:about=\"http://purl.org/dc/elements/1.1/description"
            );
    }
    
    /**
     * RDFS + CLASSIFY + application/turtle
     * 
     * @throws Exception
     */
    @Test
    public void testServiceOnlineRdfsClassifyFoafTurtle() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners/rdfs/classify","url",FOAF)
                .withHeader("Accept", "application/turtle")
        )
        .assertStatus(200)
        .assertContentType("application/turtle")
        .assertContentContains(
            "<http://xmlns.com/foaf/0.1/family_name>",
            "<http://xmlns.com/foaf/0.1/depicts>",
            "<http://xmlns.com/foaf/0.1/gender>",
            "<http://purl.org/dc/elements/1.1/title>",
            " a "
            );
    }
    
    /**
     * OWL2 + ENRICH + application/turtle
     * @throws Exception
     */
    @Test
    public void testServiceOnlineOwl2EnrichFoafTurtle() throws Exception {
        super.executor.execute(
                this.builder.buildGetRequest("/reasoners/owl2/enrich","url",FOAF)
                .withHeader("Accept", "application/turtle")
        )
        .assertStatus(200)
        .assertContentType("application/turtle")
        .assertContentRegexp(
            "rdfs:subClassOf\\s+<http://purl.org/dc/terms/Agent>",
            "owl:disjointWith\\s+<http://xmlns.com/foaf/0.1/Project>");
    }
}
