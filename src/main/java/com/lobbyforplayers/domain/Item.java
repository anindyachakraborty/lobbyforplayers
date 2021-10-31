package com.lobbyforplayers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Item.
 */
@Document(collection = "ITEM")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Item implements Serializable {

    @Id
    private String id;

    @NotNull
    @Size(max = 256)
    private String description;

    @NotNull
    private Integer views;

    @NotNull
    private String sellerName;

    @NotNull
    private String sellerId;

    @NotNull
    private Boolean listedFlag;

    @NotNull
    private Double price;

    @Size(max = 256)
    private String picturesPath;

    private String level;

    @NotNull
    private Boolean fixedPrice;

    @NotNull
    private String gameName;

    @NotNull
    @Size(min = 2, max = 10)
    private String language;

    @JsonIgnoreProperties(value = { "item" }, allowSetters = true)
    // @OneToOne
    // @JoinColumn(unique = true)
    private Order order;

    // @OneToMany(mappedBy = "item")
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ITEM" }, allowSetters = true)
    private Set<Bargain> bargains = new HashSet<>();

    // @ManyToMany
    // @JoinTable(name = "rel_item__tags", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sentries" }, allowSetters = true)
    private Set<Tags> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Item id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Item description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getViews() {
        return this.views;
    }

    public Item views(Integer views) {
        this.setViews(views);
        return this;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getSellerName() {
        return this.sellerName;
    }

    public Item sellerName(String sellerName) {
        this.setSellerName(sellerName);
        return this;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerId() {
        return this.sellerId;
    }

    public Item sellerId(String sellerId) {
        this.setSellerId(sellerId);
        return this;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Boolean getListedFlag() {
        return this.listedFlag;
    }

    public Item listedFlag(Boolean listedFlag) {
        this.setListedFlag(listedFlag);
        return this;
    }

    public void setListedFlag(Boolean listedFlag) {
        this.listedFlag = listedFlag;
    }

    public Double getPrice() {
        return this.price;
    }

    public Item price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPicturesPath() {
        return this.picturesPath;
    }

    public Item picturesPath(String picturesPath) {
        this.setPicturesPath(picturesPath);
        return this;
    }

    public void setPicturesPath(String picturesPath) {
        this.picturesPath = picturesPath;
    }

    public String getLevel() {
        return this.level;
    }

    public Item level(String level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getFixedPrice() {
        return this.fixedPrice;
    }

    public Item fixedPrice(Boolean fixedPrice) {
        this.setFixedPrice(fixedPrice);
        return this;
    }

    public void setFixedPrice(Boolean fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public String getGameName() {
        return this.gameName;
    }

    public Item gameName(String gameName) {
        this.setGameName(gameName);
        return this;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getLanguage() {
        return this.language;
    }

    public Item language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item order(Order order) {
        this.setOrder(order);
        return this;
    }

    public Set<Bargain> getBargains() {
        return this.bargains;
    }

    public void setBargains(Set<Bargain> bargains) {
        if (this.bargains != null) {
            this.bargains.forEach(i -> i.setItem(null));
        }
        if (bargains != null) {
            bargains.forEach(i -> i.setItem(this));
        }
        this.bargains = bargains;
    }

    public Item bargains(Set<Bargain> bargains) {
        this.setBargains(bargains);
        return this;
    }

    public Item addBargain(Bargain bargain) {
        this.bargains.add(bargain);
        bargain.setItem(this);
        return this;
    }

    public Item removeBargain(Bargain bargain) {
        this.bargains.remove(bargain);
        bargain.setItem(null);
        return this;
    }

    public Set<Tags> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    public Item tags(Set<Tags> tags) {
        this.setTags(tags);
        return this;
    }

    public Item addTags(Tags tags) {
        this.tags.add(tags);
        tags.getSentries().add(this);
        return this;
    }

    public Item removeTags(Tags tags) {
        this.tags.remove(tags);
        tags.getSentries().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", views=" + getViews() +
            ", sellerName='" + getSellerName() + "'" +
            ", sellerId='" + getSellerId() + "'" +
            ", listedFlag='" + getListedFlag() + "'" +
            ", price=" + getPrice() +
            ", picturesPath='" + getPicturesPath() + "'" +
            ", level='" + getLevel() + "'" +
            ", fixedPrice='" + getFixedPrice() + "'" +
            ", gameName='" + getGameName() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
