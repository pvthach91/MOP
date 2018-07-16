package com.brian.item;

public class FavoriteItemSon extends FavoriteItem {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5145194842180990305L;
	
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (obj instanceof FavoriteItemSon) {
				FavoriteItemSon son = (FavoriteItemSon) obj;
				if (son.getUrl().equals(this.getUrl())
						&& son.getLotteryid().equals(this.getLotteryid())
						&& son.getLotteryname().equals(this.getLotteryname())) {
					return true;
				}
			}
		}
		return false;
	}

}
