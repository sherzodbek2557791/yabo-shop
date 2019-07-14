<template>
  <div class="navarea">
    <nav>
      <div class="w3-bar capsule">
        <a
          href="javascript:void(0)"
          class="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium"
          @click="isNavMenuShow = !isNavMenuShow"
          >&#9776;</a
        >
        <router-link class="w3-bar-item" to="/" v-if="$route.path !== '/'">
          <img src="../assets/img/logo.png" alt="" class="logo" />
        </router-link>
        <a href="/" class="w3-bar-item w3-button" v-else>
          <img src="../assets/img/logo.png" alt="" class="logo" />
        </a>
        <template v-for="(item, index) in app.categories">
          <router-link
            :class="
              `w3-bar-item w3-button w3-hide-small ${
                isCurrentCategory(item.code) ? 'w3-button-active' : ''
              }`
            "
            :to="`/filtered?category=${item.code}`"
            :key="`key-${index}`"
          >
            <li>{{ item.title }}</li>
          </router-link>
        </template>

        <div style="flex-grow: 1;">
          <router-link
            to="/cart"
            style="position: relative; float: right; padding-right: 25px; padding-left: 5px;"
          >
            <el-badge
              :value="cartTotal"
              :class="`cart ${isPath('/cart') ? 'cart-active' : ''}`"
              :hidden="cartTotal < 1"
              class="item"
            >
              <i class="fas fa-shopping-cart" style="font-size: 18pt;"></i>
            </el-badge>
          </router-link>
        </div>
      </div>
    </nav>
    <div
      @click="isNavMenuShow = false"
      :class="
        `navigation-menu w3-hide w3-hide-large w3-hide-medium ${showMenu}`
      "
    >
      <div class="navigation-menu-content" @click.stop="">
        <div @click="isNavMenuShow = false">
          <i
            class="el-icon-close"
            style="font-size: 22px; float: right; padding: 5px;"
          ></i>
        </div>
        <br />
        <template v-for="(item, index) in app.categories">
          <div>
            <div
              class="button_nav"
              @click="isNavMenuShow = false"
              style="width: 100%;"
            >
              <router-link
                :to="`/filtered?category=${item.code}`"
                :key="`menu-key-${index}`"
              >
                {{ item.title }}
              </router-link>
            </div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      isNavMenuShow: false
    };
  },
  methods: {
    isPath(val) {
      let { path } = this.$route;
      return val === path;
    },
    isCurrentCategory(code) {
      let { category } = this.$route.query;
      return code === category;
    },
    loadList() {
      this.loading = true;
      this.$http
        .get("category/list")
        .then(({ data: categoryData }) => {
          this.app.categories = categoryData;
          this.loading = false;
        })
        .catch(error => {
          this.loading = false;
          console.error(error);
        });
    }
  },
  computed: {
    showMenu() {
      return this.isNavMenuShow ? "w3-show" : "";
    },
    cartTotal() {
      return this.$store.state.cartTotal;
    }
  },
  mounted() {
    this.loadList();
  }
};
</script>

<style scoped>
.navarea {
  overflow: hidden;
}

.capsule {
  display: flex;
  align-items: center;
  margin: 0 auto;
  height: 100%;
}

nav {
  width: 100vw;
  height: 60px;
  background: #0078ff;
  box-shadow: 0px 6px 15px 0px rgba(0, 0, 0, 0.1);
}

ul {
  padding: 0px;
  margin: 0px;
  display: flex;
  list-style: none outside none;
  justify-content: center;
  align-items: center;
}

li {
  display: flex;
  padding: 0 0px;
}

.w3-bar .w3-button {
  white-space: nowrap;
}

a,
a:visited,
a:active {
  text-decoration: none;
  color: white;
}

.cartitem {
  position: relative;
  float: right;
}

.cartcount {
  font-family: "Barlow", sans-serif;
  position: absolute;
  background: #ff2211;
  color: white;
  text-align: center;
  padding-top: 4px;
  width: 20px;
  height: 20px;
  font-size: 10px;
  margin: -5px 0 0 20px;
  border-radius: 1000px;
  font-weight: 700;
}

.logo {
  height: 35px;
  margin: 0px 0px;
}

.navigation-menu {
  background: rgba(12, 12, 12, 0.2);
  position: absolute;
  width: 100%;
  height: 100%;
  margin-right: 0px;
  z-index: 101;
  top: 0;
  right: 0;
}

.navigation-menu-content {
  background: rgb(247, 244, 244);
  width: 300px;
  height: 100%;
  overflow: auto;
  float: left;
}
.button_nav {
  display: inline-block;
  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  background: #fff;
  border: 1px solid #dcdfe633;
  color: #606266;
  -webkit-appearance: none;
  text-align: center;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  outline: 0;
  margin: 0;
  -webkit-transition: 0.1s;
  transition: 0.1s;
  font-weight: 500;
  -moz-user-select: none;
  padding: 12px 20px;
  font-size: 14px;
  border-radius: 0px;
}

.button_nav:active {
  cursor: pointer;
  background: #ededed;
  border: 1px solid #dcdfe633;
}

.w3-button:hover,
.w3-button-active {
  color: #000 !important;
  background-color: #fff !important;
  border-radius: 30px;
}

.cart:hover,
.cart-active {
  color: #00ff8d;
}

/deep/ .el-badge__content {
  background-color: #ff2211;
}
</style>
