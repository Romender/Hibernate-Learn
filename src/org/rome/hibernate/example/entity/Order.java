package org.rome.hibernate.example.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_order")
@NamedEntityGraph(name = "graph.Order.items",
        attributeNodes = @NamedAttributeNode(value = "orderItemList", subgraph = "items"),
        subgraphs = @NamedSubgraph(name = "items", attributeNodes = @NamedAttributeNode("product")))
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

}
