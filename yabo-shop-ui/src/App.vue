<template>
  <div class="app-wrapper">
    <app-navigation class="app-header" />
    <div class="app-content">
      <router-view></router-view>
    </div>
    <app-footer class="app-footer" />
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

<style>
html,
body {
  height: 100%;
  width: 100%;
  margin: 0;
}

.app-wrapper {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}

.app-header,
.app-footer {
}

.app-content {
  flex: 1;
  overflow: auto;
}
</style>
