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
      allDepartments: [
        {
          code: "15eb",
          title: "Телевизор",
          description: "",
          links: [
            {
              code: "dd1",
              outUrl: "",
              inUrl: "",
              title: "Телевизор ARTEL LED 24/90",
              description: ""
            },
            {
              code: "dd2",
              outUrl: "",
              inUrl: "",
              title: "Телевизор ARTEL LED 55/C9000",
              description: ""
            },
            {
              code: "dd3",
              outUrl: "",
              inUrl: "",
              title: "Телевизоры SHIVAKI SHIVAKI 32/9000",
              description: ""
            },
            {
              code: "dd4",
              outUrl: "",
              inUrl: "",
              title: "Телевизор SAMSUNG UE 32J SMART",
              description: ""
            },
            {
              code: "dd5",
              outUrl: "",
              inUrl: "",
              title: "Телевизор LG 32/510",
              description: ""
            },
            {
              code: "dd6",
              outUrl: "",
              inUrl: "",
              title: "Телевизор Samsung  UHD 4k LED 65NU7100",
              description: ""
            },
            {
              code: "dd7",
              outUrl: "",
              inUrl: "",
              title: "Телевизор Roison 55 4k UHD",
              description: ""
            },
            {
              code: "dd8",
              outUrl: "",
              inUrl: "",
              title: "Телевизор LG 65UK65",
              description: ""
            }
          ]
        },
        { title: "Pages" },
        { title: "Value of the Day" },
        { title: "Laptops & Computers" },
        { title: "Cameras & Photography" },
        { title: "Smart Phones & Tablets" },
        { title: "Video Games & Consoles" },
        { title: "TV & Audio" },
        { title: "Gadgets" },
        { title: "Car Electronic & GPS" },
        { title: "Accessories" },
        { title: "Buy this Theme" }
      ]
    };
  }
};

/*
{
              code: "e15e",
              title: "Телевизор ARTEL LED 24/90",
              description: "",
              price: 15000,
              currency: "SUM",
              image: ""
            }
 */
