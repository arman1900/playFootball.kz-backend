package kz.playfootball.service;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.PropertyDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.net.URL;
import javax.annotation.PostConstruct;
import kz.playfootball.controller.PlayerController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphQLService {

    private PlayerController.PlayerMutation playerMutation;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String schema = Resources.toString(url, Charsets.UTF_8);

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schema);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema
            graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);

        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
            .type(newTypeWiring("Mutation")
                .dataFetcher("registerPlayer", PropertyDataFetcher.fetching(playerMutation::registerPlayer))
                .dataFetcher("loginPlayer", PropertyDataFetcher.fetching(playerMutation::loginPlayer))
                .dataFetcher("logoutPlayer", PropertyDataFetcher.fetching(playerMutation::logoutPlayer)))
            .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
