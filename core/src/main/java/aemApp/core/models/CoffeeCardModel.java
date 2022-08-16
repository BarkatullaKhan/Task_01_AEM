package aemApp.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED)
public class CoffeeCardModel {

	@Inject
	Resource componentResource;

	public List<CoffeeCard> getCoffees() {
		List<CoffeeCard> coffeesList = new ArrayList<>();
		try {
			Resource coffeeCards = componentResource.getChild("coffeeItems");
			if (coffeeCards != null) {
				for (Resource item : coffeeCards.getChildren()) {
					CoffeeCard newCart = new CoffeeCard(item);
					coffeesList.add(newCart);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return coffeesList;
	}

	public class CoffeeCard {

		private String coffeeImg;
		private String pageLink;
		private String roasterName;
		private String itemName;
		private String description;
		private String itemPrice;

		public CoffeeCard(Resource resource) {
			try {
				if (StringUtils.isNotBlank(resource.getValueMap().get("coffeeImg", String.class))) {
					this.coffeeImg = resource.getValueMap().get("coffeeImg", String.class);
				}
				if (StringUtils.isNotBlank(resource.getValueMap().get("pageLink", String.class))) {
					this.pageLink = resource.getValueMap().get("pageLink", String.class);
				}
				if (StringUtils.isNotBlank(resource.getValueMap().get("roasterName", String.class))) {
					this.roasterName = resource.getValueMap().get("roasterName", String.class);
				}
				if (StringUtils.isNotBlank(resource.getValueMap().get("itemName", String.class))) {
					this.itemName = resource.getValueMap().get("itemName", String.class);
				}
				if (StringUtils.isNotBlank(resource.getValueMap().get("description", String.class))) {
					this.description = resource.getValueMap().get("description", String.class);
				}
				if (StringUtils.isNotBlank(resource.getValueMap().get("itemPrice", String.class))) {
					this.itemPrice = resource.getValueMap().get("itemPrice", String.class);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		public String getCoffeeImg() {
			return coffeeImg;
		}

		public String getPageLink() {
			return pageLink;
		}

		public String getRoasterName() {
			return roasterName;
		}

		public String getItemName() {
			return itemName;
		}

		public String getDescription() {
			return description;
		}

		public String getItemPrice() {
			return itemPrice;
		}
	}

}
