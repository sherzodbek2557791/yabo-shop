<template>
  <div>
    <div class="item" @click="showImages">
      <p>{{ item.title }}</p>
      <span class="salepill" v-if="item.description">{{
        $t("item.installmentPlan")
      }}</span>
      <img
        class="img-product"
        :src="`/${item.image}`"
        :alt="`Image of ${item.title}`"
      />
      <p>{{ item.price | currencyFilter }}</p>
      <el-button
        @click.stop="addItem"
        icon="fas fa-shopping-cart"
        type="danger"
        round
      >
        {{ $t("index.addItem") }}
      </el-button>
    </div>
    <el-dialog :title="item.title" :visible.sync="dialogVisible">
      <el-carousel arrow="always">
        <el-carousel-item>
          <img
            class="img-product-big"
            :src="`/${item.image}`"
            :alt="`Image of ${item.title}`"
          />
        </el-carousel-item>
      </el-carousel>

      <p>{{ item.price | currencyFilter }}</p>
      <el-button
        @click.stop="addItem"
        icon="fas fa-shopping-cart"
        type="danger"
        round
      >
        {{ $t("index.addItem") }}
      </el-button>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogVisible: false
    };
  },
  props: {
    item: {
      type: Object,
      required: true
    },
    index: {
      type: Number,
      required: true
    }
  },
  filters: {
    usdollar: function(value) {
      return `$${value}`;
    },
    currencyFilter: function(value) {
      return `${value} сум`;
    }
  },
  methods: {
    showImages() {
      this.dialogVisible = true;
    },
    addItem() {
      this.$store.commit("addItem", this.item);
    }
  }
};
</script>

<style scoped>
.item {
  border-radius: 5px;
  padding: 20px;
  background: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  box-shadow: 0px 6px 15px 0px rgba(0, 0, 0, 0.1);
  transition: box-shadow 500ms;
  cursor: pointer;
}

.item:hover {
  box-shadow: 0px 6px 15px 0px rgba(0, 0, 0, 0.5);
}

.salepill {
  background: rgb(232, 35, 25);
  color: white;
  font-family: "Barlow", sans-serif;
  position: absolute;
  right: 30px;
  top: 60px;
  padding: 2px 10px 4px;
  text-transform: uppercase;
  font-size: 13px;
  font-weight: 700;
  border-radius: 1000px;
}

p {
  font-size: 18px;
}

/deep/ .el-button--danger {
  color: #ffffff;
  background-color: rgb(232, 35, 25);
  border-color: rgb(232, 35, 25);
}

/deep/ .el-button--danger:hover,
.el-button--danger:focus {
  background: #f78989;
  border-color: #f78989;
  color: #ffffff;
}

/deep/ .el-dialog__body,
.el-carousel__item {
  text-align: center;
}
</style>
