<template>
  <div>
    <div class="wrapper">
      <app-navigation />
      <router-view></router-view>
      <div class="clear"></div>
      <div class="push"></div>
    </div>
    <app-footer class="footer" />
  </div>
</template>

<script>
import AppNavigation from "./components/AppNavigation.vue";
import AppFooter from "./components/AppFooter.vue";
export default {
  name: "app",
  components: {
    AppNavigation,
    AppFooter
  }
};
</script>

<style scoped></style>
