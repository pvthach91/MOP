package com.brian.item;
import java.util.ArrayList;
import java.util.List;

//Jack add 20180611 支付类型
public class BankPayTypeItem {

    //支付类型标识
    private String icon="";
    //支付类型
    private String tab="";
    //支付类型标识名称
    private String iconlable="";


    //对应的支付方式列表
    private List<AdminBankCardItem> AdminBankCardItemList=new ArrayList<AdminBankCardItem>();

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getIconlable() {
        return iconlable;
    }
    public void setIconlable(String iconlable) {
        this.iconlable = iconlable;
    }

    public List<AdminBankCardItem> getAdminBankCardItemList() {
        return AdminBankCardItemList;
    }

    public void setAdminBankCardItemList(List<AdminBankCardItem> adminBankCardItemList) {
        AdminBankCardItemList = adminBankCardItemList;
    }

}
