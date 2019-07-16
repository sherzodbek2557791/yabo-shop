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
            <el-select
              v-model="form.regionSoato"
              placeholder=""
              no-data-text="No data"
              @change="onChangeRegion"
              clearable
            >
              <el-option
                v-for="item in regionList"
                :key="item['soato']"
                :label="item['nameRuRu']"
                :value="item['soato']"
              >
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
            <el-select
              v-model="form.areaSoato"
              placeholder=""
              no-data-text="No data"
              clearable
            >
              <el-option
                v-for="item in areaList"
                :key="item['soato']"
                :label="item['nameRuRu']"
                :value="item['soato']"
              >
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
              <el-radio :label="PaymentType.CASH">{{
                $t("cart.form.paymentTypes.CASH")
              }}</el-radio>
              <el-radio :label="PaymentType.CLICK">{{
                $t("cart.form.paymentTypes.CLICK")
              }}</el-radio>
              <el-radio :label="PaymentType.INSTALLMENT_PLAN">{{
                $t("cart.form.paymentTypes.INSTALLMENT_PLAN")
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item
            v-if="form.paymentType === PaymentType.INSTALLMENT_PLAN"
            style="padding-left: 35px;"
            prop="paymentType"
            :rules="rules.paymentType"
          >
            <el-radio-group v-model="form.installmentPlan">
              <el-radio label="4">{{
                Utils.stringFormat($t("cart.minMonth"), 4, 1900, "сум")
              }}</el-radio>
              <el-radio label="6">{{
                Utils.stringFormat($t("cart.minMonth"), 6, 1901, "сум")
              }}</el-radio>
              <el-radio label="8">{{
                Utils.stringFormat($t("cart.minMonth"), 8, 1902, "сум")
              }}</el-radio>
              <el-radio label="10">{{
                Utils.stringFormat($t("cart.minMonth"), 10, 1903, "сум")
              }}</el-radio>
              <el-radio label="12">{{
                Utils.stringFormat($t("cart.minMonth"), 12, 1904, "сум")
              }}</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-row v-if="form.step === Step.SECOND">
            <fieldset class="buyer-fieldset">
              <legend>{{ $t("cart.form.uploadPayerData") }}</legend>
              <el-row :gutter="20">
                <el-col :span="8">
                  <!-- <el-upload
                    class="avatar-uploader"
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                  >
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>-->
                  <el-form-item
                    :label="$t('cart.form.passportFront')"
                    prop="paymentType"
                    size="small"
                    :rules="rules.paymentType"
                  >
                    <el-upload
                      class="avatar-uploader"
                      action="https://jsonplaceholder.typicode.com/posts/"
                      :show-file-list="false"
                    >
                      <i class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    :label="$t('cart.form.passportBack')"
                    prop="paymentType"
                    :rules="rules.paymentType"
                  >
                    <el-upload
                      class="avatar-uploader"
                      action="https://jsonplaceholder.typicode.com/posts/"
                      :show-file-list="false"
                    >
                      <i class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    :label="$t('cart.form.salaryReport')"
                    prop="paymentType"
                    :rules="rules.paymentType"
                  >
                    <el-upload
                      class="avatar-uploader"
                      action="https://jsonplaceholder.typicode.com/posts/"
                      :show-file-list="false"
                    >
                      <i class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                </el-col>
              </el-row>
            </fieldset>
            <fieldset class="buyer-fieldset" style="margin-top: 10px;">
              <legend>{{ $t("cart.form.uploadGuarantorData") }}</legend>
              <el-row :gutter="20">
                <el-col :span="8">
                  <!-- <el-upload
                    class="avatar-uploader"
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                  >
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>-->
                  <el-form-item
                    :label="$t('cart.form.passportFront')"
                    prop="paymentType"
                    size="small"
                    :rules="rules.paymentType"
                  >
                    <el-upload
                      class="avatar-uploader"
                      action="https://jsonplaceholder.typicode.com/posts/"
                      :show-file-list="false"
                    >
                      <i class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    :label="$t('cart.form.passportBack')"
                    prop="paymentType"
                    :rules="rules.paymentType"
                  >
                    <el-upload
                      class="avatar-uploader"
                      action="https://jsonplaceholder.typicode.com/posts/"
                      :show-file-list="false"
                    >
                      <i class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item
                    :label="$t('cart.form.salaryReport')"
                    prop="paymentType"
                    :rules="rules.paymentType"
                  >
                    <el-upload
                      class="avatar-uploader"
                      action="https://jsonplaceholder.typicode.com/posts/"
                      :show-file-list="false"
                    >
                      <i class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </el-form-item>
                </el-col>
              </el-row>
            </fieldset>
          </el-row>

          <el-form-item style="text-align: center; margin-top: 10px;">
            <el-button type="success" @click="confirmOrder" round>{{
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
import Utils from "../util/Utils";

const PaymentType = Object.freeze({
  CASH: Symbol("CASH"),
  CLICK: Symbol("CLICK"),
  INSTALLMENT_PLAN: Symbol("INSTALLMENT_PLAN")
});

const Step = Object.freeze({
  FIRST: Symbol("first"),
  SECOND: Symbol("second")
});

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
      PaymentType,
      Step,
      Utils,
      submitted: false,
      complete: true,
      status: "",
      response: "",
      stripeOptions: {},
      stripeEmail: "",
      form: {
        step: Step.FIRST,
        regionSoato: null,
        areaSoato: null,
        fullName: null,
        phoneNumber: null,
        paymentType: null,
        installmentPlan: null
      },
      rules: {
        regionSoato: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "blur"
          }
        ],
        areaSoato: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "blur"
          }
        ],
        fullName: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "blur"
          }
        ],
        phoneNumber: [
          {
            required: true,
            message: this.$t("general.fillField"),
            trigger: "blur"
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
            trigger: "blur"
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
      areaList: []
    };
  },
  methods: {
    onChangeRegion(val) {
      this.form.areaSoato = null;
      if (val) this.loadAreas(val);
      else this.areaList = [];
    },
    loadRegions() {
      this.$http
        .get("region/list-region")
        .then(({ data }) => {
          this.regionList = data;
        })
        .catch(error => console.error(error));
    },
    loadAreas(parentSoato) {
      this.$http
        .get("region/list-area", { parentSoato })
        .then(({ data }) => {
          this.areaList = data;
        })
        .catch(error => console.error(error));
    },
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
    confirmOrder() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          this.$message({
            showClose: true,
            message: this.$t("cart.form.fillError"),
            type: "error"
          });
          return false;
        }

        let { step, paymentType } = this.form;

        switch (paymentType) {
          case PaymentType.CASH:
            this.sendOrder();
            break;
          case PaymentType.CLICK:
            alert("sorry click not working yet!");
            break;
          case PaymentType.INSTALLMENT_PLAN:
            switch (step) {
              case Step.FIRST:
                this.$set(this.form, "step", Step.SECOND);
                break;
              case Step.SECOND:
                this.sendOrder();
                break;
            }
            break;
        }
      });
    },

    sendOrder() {
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
      // this.$refs["formEmail"].resetField();
      // this.$refs["formFirstName"].resetField();
      // this.$refs["formLastName"].resetField();
      // this.$refs["formMessage"].resetField();
    }
  },
  mounted() {
    this.resetForm();
    this.loadForm();
    this.loadRegions();
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
/deep/ .el-input,
.el-select {
  width: 100%;
}
/deep/ .el-radio-group {
  display: grid;
}

.avatar-uploader /deep/ .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100%;
}
.avatar-uploader /deep/ .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  /*width: 100px;*/
  /*height: 100px;*/
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}

.buyer-fieldset {
  border: 1px dashed #cccccc;
  border-radius: 4px;
  -moz-border-radius: 4px;
  -webkit-border-radius: 4px;
  font-size: 10pt;
}

.buyer-fieldset /deep/ .el-col {
  text-align: center;
}
.buyer-fieldset /deep/ .el-row {
  margin-left: 0px;
  margin-right: 0px;
}
.buyer-fieldset /deep/ .el-form-item {
  display: flex;
  flex-direction: column-reverse;
}
.buyer-fieldset /deep/ .el-form-item__label {
  line-height: 15px;
  font-size: 9pt;
  font-style: italic;
}
.buyer-fieldset /deep/ .el-form-item__content {
  flex-grow: 1;
}
</style>
