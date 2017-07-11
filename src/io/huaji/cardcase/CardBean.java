package io.huaji.cardcase;

import java.io.Serializable;

public class CardBean implements Serializable{

    private String cardTel;
    private String cardName;
    private String cardSex;
    private String cardPosition;
    private String cardCorp;

    public void setCardTel(String cardTel) {
        this.cardTel = cardTel;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardSex(String cardSex) {
        this.cardSex = cardSex;
    }

    public void setCardPosition(String cardPosition) {
        this.cardPosition = cardPosition;
    }

    public void setCardCorp(String cardCorp) {
        this.cardCorp = cardCorp;
    }

    public String getCardTel() {

        return cardTel;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardSex() {
        return cardSex;
    }

    public String getCardPosition() {
        return cardPosition;
    }

    public String getCardCorp() {
        return cardCorp;
    }

    public CardBean(String cardTel, String cardName, String cardSex, String cardPosition, String cardCorp) {

        this.cardTel = cardTel;
        this.cardName = cardName;
        this.cardSex = cardSex;
        this.cardPosition = cardPosition;
        this.cardCorp = cardCorp;
    }



}
