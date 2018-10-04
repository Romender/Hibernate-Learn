package org.rome.hibernate.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name="graph.flowDetails",
    attributeNodes = {@NamedAttributeNode(value = "marketChannelFlowDetails" ,subgraph = "subgraph.marketFlow"),
        @NamedAttributeNode(value = "planningDetails"),@NamedAttributeNode(value = "seasonCode")},
    subgraphs = {@NamedSubgraph(name = "subgraph.marketFlow",attributeNodes = {@NamedAttributeNode("name"),@NamedAttributeNode("flowDetails")}),
                 @NamedSubgraph(name ="subgraph.flowHierarchyMap",attributeNodes = @NamedAttributeNode("name"))})
public class FlowDetails  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "flow_id")
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "planning_details_id")
    private PlanningDetails planningDetails;

    @ManyToOne
    @JoinColumn(name = "season_code")
    private SeasonCode seasonCode;

    @Builder.Default
    @OneToMany( cascade = CascadeType.ALL)
    private List<FlowDetailsHierarchyNodeMap> flowDetailsHierarchyNodeMaps = new ArrayList<>();

    @Builder.Default
    @OneToMany( cascade = CascadeType.ALL)
    private  List<MarketChannelFlowDetails> marketChannelFlowDetails = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FlowDetails that = (FlowDetails) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(id, that.id)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(id)
                .append(name)
                .toHashCode();
    }

    public void addMarketChannelFlowDetails(MarketChannelFlowDetails marketChannelFlowDetail) {
        this.marketChannelFlowDetails.add(marketChannelFlowDetail);
        marketChannelFlowDetail.setFlowDetails(this);
    }

    public void addFlowDetailsHierarchyNodeMap(FlowDetailsHierarchyNodeMap flowDetailsHierarchyNodeMap) {
        this.flowDetailsHierarchyNodeMaps.add(flowDetailsHierarchyNodeMap);
        flowDetailsHierarchyNodeMap.setFlowDetails(this);
    }
}
