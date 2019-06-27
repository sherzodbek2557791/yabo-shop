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
            class="w3-bar-item w3-button w3-hide-small"
            :to="`/filtered?category=${item.code}`"
            :key="`key-${index}`"
          >
            <li>{{ item.title }}</li>
          </router-link>
        </template>

        <div style="flex-grow: 1;">
          <router-link to="/cart">
            <div class="cartitem w3-bar-item w3-button w3-right">
              <div v-if="cartTotal > 0" class="cartcount">{{ cartTotal }}</div>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 100 100"
                aria-labelledby="shopicon"
                role="presentation"
                width="30"
                height="30"
              >
                <title id="cart">
                  {{ $t("cart.title") }}
                </title>
                <path
                  fill="black"
                  d="M8.01 10c-1.104 0-2 .896-2 2 0 1.105.896 2 2 2h10.376l10.53 49.813c-.107 1.14.952 2.245 2.095 2.187h50c1.057.015 2.03-.943 2.03-2s-.973-2.015-2.03-2H32.637l-1.688-8H85.01c.896-.01 1.742-.69 1.938-1.562l7-30c.26-1.16-.748-2.43-1.937-2.438H23.76l-1.78-8.437c-.2-.884-1.063-1.57-1.97-1.563zm16.594 14H89.51l-6.093 26H30.104zM42.01 72c-4.946 0-9 4.053-9 9s4.054 9 9 9c4.948 0 9-4.053 9-9s-4.052-9-9-9zm28 0c-4.946 0-9 4.053-9 9s4.054 9 9 9c4.948 0 9-4.053 9-9s-4.052-9-9-9zm-28 4c2.786 0 5 2.215 5 5s-2.214 5-5 5c-2.784 0-5-2.215-5-5s2.216-5 5-5zm28 0c2.786 0 5 2.215 5 5s-2.214 5-5 5c-2.784 0-5-2.215-5-5s2.216-5 5-5z"
                />
              </svg>
            </div>
          </router-link>
        </div>
      </div>

      <!--<div class="capsule">
        <router-link to="/" v-if="$route.path !== '/'">
          <img src="../assets/img/logo.png" alt="" class="logo" />
        </router-link>
        <a href="/" v-else>
          <img src="../assets/img/logo.png" alt="" class="logo" />
        </a>
        <ul v-loading="loading">
          <template v-for="item in app.categories">
            <router-link
              :to="`/filtered?category=${item.code}`"
              :key="item.code"
            >
              <li>{{ item.title }}</li>
            </router-link>
          </template>
          <template v-for="item in app.categories">
            <router-link
                    :to="`/filtered?category=${item.code}`"
                    :key="`key1-${item.code}`"
            >
              <li>{{ item.title }}</li>
            </router-link>
          </template>
          <template v-for="item in app.categories">
            <router-link
                    :to="`/filtered?category=${item.code}`"
                    :key="`key2-${item.code}`"
            >
              <li>{{ item.title }}</li>
            </router-link>
          </template>
        </ul>
        <router-link to="/cart">
          <div class="cartitem">
            <div v-if="cartTotal > 0" class="cartcount">{{ cartTotal }}</div>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 100 100"
              aria-labelledby="shopicon"
              role="presentation"
              width="30"
              height="30"
            >
              <title id="cart">
                {{ $t("cart.title") }}
              </title>
              <path
                fill="black"
                d="M8.01 10c-1.104 0-2 .896-2 2 0 1.105.896 2 2 2h10.376l10.53 49.813c-.107 1.14.952 2.245 2.095 2.187h50c1.057.015 2.03-.943 2.03-2s-.973-2.015-2.03-2H32.637l-1.688-8H85.01c.896-.01 1.742-.69 1.938-1.562l7-30c.26-1.16-.748-2.43-1.937-2.438H23.76l-1.78-8.437c-.2-.884-1.063-1.57-1.97-1.563zm16.594 14H89.51l-6.093 26H30.104zM42.01 72c-4.946 0-9 4.053-9 9s4.054 9 9 9c4.948 0 9-4.053 9-9s-4.052-9-9-9zm28 0c-4.946 0-9 4.053-9 9s4.054 9 9 9c4.948 0 9-4.053 9-9s-4.052-9-9-9zm-28 4c2.786 0 5 2.215 5 5s-2.214 5-5 5c-2.784 0-5-2.215-5-5s2.216-5 5-5zm28 0c2.786 0 5 2.215 5 5s-2.214 5-5 5c-2.784 0-5-2.215-5-5s2.216-5 5-5z"
              />
            </svg>
          </div>
        </router-link>
      </div>-->
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
  /*justify-content: space-between;*/
  align-items: center;
  /*width: 50vw;*/
  margin: 0 auto;
}

nav {
  width: 100vw;
  height: 60px;
  background: white;
}

ul {
  padding-left: 0;
  display: flex;
  list-style: none outside none;
  justify-content: center;
  align-items: center;
}

li {
  padding: 0 50px;
}

a,
a:visited,
a:active {
  text-decoration: none;
  color: black;
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
  height: 45px;
  margin: 0px;
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
</style>
