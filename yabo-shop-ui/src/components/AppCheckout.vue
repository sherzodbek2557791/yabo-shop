<template>
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
            prop="phoneNumber"
            :label="$t('cart.form.phoneNumber')"
            :rules="rules.phoneNumber"
            required
          >
            <el-input v-model="form.phoneNumber"></el-input>
          </el-form-item>

          <el-form-item
            prop="email"
            :label="$t('cart.form.email')"
            :rules="rules.email"
            required
          >
            <el-input v-model="form.email"></el-input>
          </el-form-item>

          <el-form-item>
            <el-col :span="11">
              <el-form-item
                prop="firstName"
                :label="$t('cart.form.firstName')"
                required
              >
                <el-input v-model="form.firstName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <div style="padding-bottom: 1px;"></div>
            </el-col>
            <el-col :span="11">
              <el-form-item
                prop="lastName"
                :label="$t('cart.form.lastName')"
                required
              >
                <el-input v-model="form.lastName"></el-input>
              </el-form-item>
            </el-col>
          </el-form-item>

          <el-form-item
            prop="message"
            :label="$t('cart.form.message')"
            required
          >
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              v-model="form.message"
            >
            </el-input>
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
        phoneNumber: null,
        email: null,
        firstName: null,
        lastName: null,
        message: null
      },
      rules: {
        phoneNumber: [],
        email: [],
        firstName: [],
        lastName: [],
        message: []
      }
    };
  },
  methods: {
    cancelOrder() {
      this.$confirm(
        "This will permanently delete the items. Continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
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
        //this.$store.state.cart
        let form = { ...this.form };
        let request = Object.assign(form, { orderItems: [] });

        console.log(request);

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

            //console logs for you :)
            // this.response = JSON.stringify(response, null, 2);
            console.log(data);
          })
          .catch(error => {
            this.status = "failure";

            //console logs for you :)
            // this.response = "Error: " + JSON.stringify(error, null, 2);
            console.error(error);
          });
      });
      /*createToken().then(data => {
        this.submitted = true;
        console.log(data.token); //this is a token we would use for the stripeToken below
        axios
          .post(
            "https://sdras-stripe.azurewebsites.net/api/charge?code=zWwbn6LLqMxuyvwbWpTFXdRxFd7a27KCRCEseL7zEqbM9ijAgj1c1w==",
            {
              stripeEmail: this.stripeEmail,
              stripeToken: "tok_visa", //testing token
              stripeAmt: this.total
            },
            {
              headers: {
                "Content-Type": "application/json"
              }
            }
          )
          .then(response => {
            this.status = "success";
            this.$emit("successSubmit");
            this.$store.commit("clearCartCount");

            //console logs for you :)
            this.response = JSON.stringify(response, null, 2);
            console.log(this.response);
          })
          .catch(error => {
            this.status = "failure";

            //console logs for you :)
            this.response = "Error: " + JSON.stringify(error, null, 2);
            console.log(this.response);
          });
      });*/
    },
    clearCart() {
      this.submitted = false;
      this.status = "";
      this.complete = false;
      this.response = "";
    }
  },
  mounted() {
    this.form.phoneNumber = this.$store.state.form.phoneNumber;
    this.form.email = this.$store.state.form.email;
    this.form.firstName = this.$store.state.form.firstName;
    this.form.lastName = this.$store.state.form.lastName;
    this.form.message = this.$store.state.form.message;
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
  margin-bottom: 0px;
}

/deep/ .el-form--label-top .el-form-item__label {
  padding: 0px;
}

/deep/ .el-form {
  text-align: left;
}
</style>
