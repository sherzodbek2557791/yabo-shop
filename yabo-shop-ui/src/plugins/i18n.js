import Vue from "vue";
import VueI18n from "vue-i18n";
import FlagIcon from "vue-flag-icon";
import messages from "./messages";

Vue.use(VueI18n);
Vue.use(FlagIcon);

export default {
  plugin: new VueI18n({
    locale: "ru",
    fallbackLocale: "ru",
    messages
  }),
  localeTitle(code) {
    for (let lang of this.languages) {
      if (lang.code === code) return lang.title;
    }
    return code;
  },
  localeFlag(code) {
    for (let lang of this.languages) {
      if (lang.code === code) return lang.flag;
    }
    return code;
  },
  languages: [
    { flag: "ru", code: "ru", title: "Русский" }
    // { flag: "uz", code: "uz", title: "O'zbek" },
    // { flag: "us", code: "en", title: "English" }
  ]
};
