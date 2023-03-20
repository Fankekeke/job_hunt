<template>
  <a-modal v-model="show" title="用户订单" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="userData !== null">
      <div style="background:#ECECEC; padding:24px;font-size: 13px;">
        <a-row :gutter="15">
          <a-col :span="12" v-for="(item, index) in orderList" :key="index" style="margin-top: 15px">
            <a-card hoverable>
              <a-card-meta :title="item.shopName + '的小店'" :description="item.name">
                <a-avatar
                  slot="avatar"
                  :src="item.shopAvatar"
                />
              </a-card-meta>
              <div style="margin-left: 45px;margin-top: 20px">
                <a-row :gutter="8">
                  <a-col :span="8"><b>价 格：</b>￥{{ item.price }}</a-col>
                  <a-col :span="16"><b>下单时间：</b>{{ item.createDate }}</a-col>
                </a-row>
              </div>
            </a-card>
          </a-col>
          <a-col v-if="orderList.length === 0">
            无订单
          </a-col>
        </a-row>
      </div>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
export default {
  name: 'userView',
  props: {
    userShow: {
      type: Boolean,
      default: false
    },
    userData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.userShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      orderList: []
    }
  },
  watch: {
    userShow: function (value) {
    }
  },
  methods: {
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
