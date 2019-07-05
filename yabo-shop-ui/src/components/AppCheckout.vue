<template>

  <!--
  region_soato
  area_soato
  full_name
  phone_number
  payment_type
  PAYMENT_DETAILS ->
  payer_passport_front
  payer_passport_back
  payer_salary_report
  guarantor_passport_front
  guarantor_passport_back
  guarantor_salary_report
 -->

  <div>
    <transition name="fade">
      <div v-if="!submitted" class="payment">
        <h3>{{ $t("cart.payment.details") }}:</h3>
        <el-form
          ref="form"
          :model="form"
          label-position="top"
          label-width="100%"
          size="small"
        >
          <el-form-item
            prop="regionSoato"
            :label="$t('cart.form.regionSoato')"
            :rules="rules.regionSoato"
            :placeholder="$t('general.fillField')"
            ref="formRegionSoato"
          >
            <el-select v-model="form.regionSoato" placeholder="" no-data-text="No data" clearable>
              <el-option
                      v-for="item in regionList"
                      :key="item['soato']"
                      :label="item['name']"
                      :value="item['soato']">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            prop="regionSoato"
            :label="$t('cart.form.areaSoato')"
            :rules="rules.areaSoato"
            :placeholder="$t('general.fillField')"
            ref="formAreaSoato"
          >
            <el-select v-model="form.areaSoato" placeholder="" no-data-text="No data" clearable>
              <el-option
                      v-for="item in areaList"
                      :key="item['soato']"
                      :label="item['name']"
                      :value="item['soato']">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            prop="fullName"
            :label="$t('cart.form.fullName')"
            :rules="rules.fullName"
            :placeholder="$t('general.fillField')"
            ref="formFullName"
          >
            <el-input v-model="form.fullName" clearable></el-input>
          </el-form-item>
          <el-form-item
            prop="phoneNumber"
            :label="$t('cart.form.phoneNumber')"
            :rules="rules.phoneNumber"
            :placeholder="$t('general.fillField')"
            v-regex="'^\\+998\\d{9}$'"
            clearable
            ref="formPhoneNumber"
          >
            <el-input v-model="form.phoneNumber"></el-input>
          </el-form-item>
          <el-form-item
                  prop="paymentType"
                  :label="$t('cart.form.paymentType')"
                  :rules="rules.paymentType"
                  :placeholder="$t('general.fillField')"
                  ref="formPaymentType"
          >
            <el-radio-group v-model="form.paymentType">
              <el-radio label="CASH">{{ $t('cart.form.paymentTypes.CASH') }}</el-radio>
              <el-radio label="CLICK">{{ $t('cart.form.paymentTypes.CLICK') }}</el-radio>
              <el-radio label="INSTALLMENT_PLAN">{{ $t('cart.form.paymentTypes.INSTALLMENT_PLAN') }}</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item style="text-align: center; margin-top: 10px;">
            <el-button type="success" @click="sendOrder" round>{{
              $t("cart.form.submit")
            }}</el-button>
            <el-button type="info" @click="cancelOrder" round>{{
              $t("cart.form.cancel")
            }}</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div v-else class="statussubmit">
        <div v-if="status === 'failure'">
          <h3>Oh No!</h3>
          <p>Something went wrong!</p>
          <button @click="clearCart">Please try again</button>
        </div>

        <div v-else class="loadcontain">
          <h4>Please hold, we're filling up your cart with goodies</h4>
          <app-loader />
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import AppLoader from "./AppLoader.vue";

export default {
  components: {
    AppLoader
  },
  props: {
    total: {
      type: [Number, String],
      default: "50.00"
    },
    success: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      submitted: false,
      complete: true,
      status: "",
      response: "",
      stripeOptions: {},
      stripeEmail: "",
      form: {
        regionSoato: null,
        areaSoato: null,
        fullName: null,
        phoneNumber: null,
        paymentType: null,
      },
      rules: {
        regionSoato: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "change"
          }
        ],
        areaSoato: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "change"
          }
        ],
        fullName: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "change"
          }
        ],
        phoneNumber: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "change"
          },
          {
            validator: (rule, value, callback) => {
              let res = value.replace(/\D/g, "");
              if (res.length !== 12) {
                callback(new Error(this.$t("cart.form.fillValidPhoneNumber")));
              } else {
                callback();
              }
            },
            trigger: "change"
          }
        ],
        paymentType: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "change"
          }
        ]
      },
      regionList: [],
      areaList: [],
    };
  },
  methods: {
    cancelOrder() {
      this.$confirm(
        this.$t("cart.form.attentionRemoveAllOrders"),
        this.$t("general.attention"),
        {
          confirmButtonText: this.$t("general.ok"),
          cancelButtonText: this.$t("general.cancel"),
          type: "warning"
        }
      )
        .then(() => {
          this.$store.commit("clearCart");
          this.$message({
            type: "success",
            message: "Delete completed"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Delete canceled"
          });
        });
    },
    sendOrder() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          this.$message({
            showClose: true,
            message: this.$t("cart.form.fillError"),
            type: "error"
          });
          return false;
        }

        let { cart } = this.$store.state;
        let orderItems = Object.keys(cart).map(k => cart[k]);
        let form = { ...this.form };
        let request = Object.assign(form, { orderItems });

        this.$http
          .post("order/create", request)
          .then(({ data }) => {
            this.status = "success";
            this.$emit("successSubmit");
            this.$store.commit("clearCart");
            this.$message({
              showClose: true,
              message: this.$t("cart.form.success"),
              type: "success"
            });
            this.$store.commit("setForm", this.form);
          })
          .catch(error => {
            this.status = "failure";
            console.error(error);
          });
      });
    },
    clearCart() {
      this.submitted = false;
      this.status = "";
      this.complete = false;
      this.response = "";
    },
    loadForm() {
      let {
        phoneNumber,
        email,
        firstName,
        lastName,
        message
      } = this.$store.state.form;
      this.form.phoneNumber = phoneNumber;
      this.form.email = email;
      this.form.firstName = firstName;
      this.form.lastName = lastName;
      this.form.message = message;
    },
    resetForm() {
      this.$refs["formRegionSoato"].resetField();
      this.$refs["formAreaSoato"].resetField();
      this.$refs["formFullName"].resetField();
      this.$refs["formPhoneNumber"].resetField();
      //todo remove
      this.$refs["formEmail"].resetField();
      this.$refs["formFirstName"].resetField();
      this.$refs["formLastName"].resetField();
      this.$refs["formMessage"].resetField();
    }
  },
  mounted() {
    this.resetForm();
    this.loadForm();
  }
};
</script>

<style scoped>
.payment {
  border: 1px solid #ccc;
  color: black;
  width: 500px;
  padding: 50px;
  display: flex;
  flex-direction: column;
  min-height: 350px;
  justify-content: space-between;
  text-align: center;
}
.statussubmit {
  text-align: center;
}
.stripe-card {
  border: 1px solid #ccc;
}
p {
  margin: 0 0 10px;
}
label {
  color: black;
  margin: 15px 0 5px;
  font-family: "Playfair Display", sans-serif;
}
.loadcontain {
  text-align: center;
}
.stripe-card {
  margin-bottom: 10px;
}
.cc-number {
  color: #3964e8;
  font-weight: bold;
}

/* -- transition --*/
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.25s ease-out;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

/deep/ .el-form-item {
  margin-bottom: 10px;
}

/deep/ .el-form--label-top .el-form-item__label {
  padding: 0px;
}

/deep/ .el-form {
  text-align: left;
}
/deep/ .el-input, .el-select {
  width: 100%;
}
/deep/ .el-radio-group {
  display: grid;
}
</style>
