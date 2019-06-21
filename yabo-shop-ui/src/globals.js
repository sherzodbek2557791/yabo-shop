import products from "./reference/products";

export default {
  get app() {
    return {
      supportPhone: "+998 97 922 50 00",
      supportEmail: "support@yabo.uz",
      currencies: [
        {
          currency: "USD",
          title: "Dollar",
          format(amount) {
            return `$ ${amount}`;
          }
        },
        {
          currency: "SUM",
          title: "So'm",
          format(amount) {
            return `${amount} so'm`;
          }
        }
      ],
      products,
      getters: {
        get women() {
          return filter(products, "category", "women");
        },
        get men() {
          return filter(products, "category", "men");
        },
        get sale() {
          return filter(products, "sale", true);
        }
      }
    };
  }
};
// helper
const filter = (array, key, value) => array.filter(item => item[key] === value);
