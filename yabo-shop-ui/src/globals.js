export default {
  get app() {
    return {
      supportPhone: "+998 97 922 50 00",
      supportEmail: "support@yabo.uz",
      currencies: [{ currency: "USD", title: "Dollar", format(amount){ return `$ ${amount}`;} }, { currency: "SUM", title: "So'm", format(amount){ return `${amount} so'm`;} }],
      allDepartments: [
        { name: "Pages" },
        { name: "Value of the Day" },
        { name: "Laptops & Computers" },
        { name: "Cameras & Photography" },
        { name: "Smart Phones & Tablets" },
        { name: "Video Games & Consoles" },
        { name: "TV & Audio" },
        { name: "Gadgets" },
        { name: "Car Electronic & GPS" },
        { name: "Accessories" },
        { name: "Buy this Theme" }
      ]
    };
  }
};
