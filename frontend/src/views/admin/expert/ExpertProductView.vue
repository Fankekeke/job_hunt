<template>
  <a-modal v-model="show" title="求职者项目信息" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei">
      <div style="font-size: 13px;font-family: SimHei" v-if="expertData !== null">
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">求职者信息</span></a-col>
          <a-col :span="8"><b>求职者姓名：</b>
            {{ expertData.name }}
          </a-col>
          <a-col :span="8"><b>民族：</b>
            {{ expertData.nationality }}
          </a-col>
          <a-col :span="8"><b>性别：</b>
            {{ expertData.sex }}
          </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col :span="8"><b>政治面貌：</b>
            {{ expertData.politicalStatus }}
          </a-col>
          <a-col :span="8"><b>籍贯：</b>
            {{ expertData.nativePlace }}
          </a-col>
          <a-col :span="8"><b>工作单位：</b>
            {{ expertData.employer }}
          </a-col>
        </a-row>
        <br/>
        <a-row style="padding-left: 24px;padding-right: 24px;">
          <a-col :span="8"><b>职务：</b>
            {{ expertData.position }}
          </a-col>
          <a-col :span="16"><b>通讯地址：</b>
            {{ expertData.address }}
          </a-col>
          <br/>
          <br/>
          <a-col :span="8"><b>手机号：</b>
            {{ expertData.phone }}
          </a-col>
          <a-col :span="8"><b>专业方向一级：</b>
            {{ expertData.levelOne }}
          </a-col>
        </a-row>
        <br/>
      </div>
      <a-collapse default-active-key="1" :bordered="false" v-if="expertProductList.length !== 0">
        <a-collapse-panel :header="item.productName" :style="customStyle" v-for="(item, index) in expertProductList" :key="index">
          <a-row style="padding-left: 24px;padding-right: 24px;font-size: 13px;font-family: SimHei">
            <a-col :span="24"><b>内容研究：</b>
              {{ item.research }}
            </a-col>
            <br/>
          </a-row>
          <br/>
          <a-row style="padding-left: 24px;padding-right: 24px;font-size: 13px;font-family: SimHei">
            <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">关键技术</span></a-col>
            <a-col :span="24">
              {{ item.technology }}
            </a-col>
          </a-row>
          <br/>
          <a-row style="padding-left: 24px;padding-right: 24px;font-size: 13px;font-family: SimHei">
            <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">项目内容</span></a-col>
            <a-col :span="24">
              {{ item.content }}
            </a-col>
          </a-row>
          <br/>
        </a-collapse-panel>
      </a-collapse>
      <div v-else style="font-size: 25px;margin: 25px;text-align: center">
        暂无项目成果
      </div>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'expertView',
  props: {
    expertShow: {
      type: Boolean,
      default: false
    },
    expertData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.expertShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      activeKey: ['1'],
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      expertProductList: [],
      text: `A dog is a type of domesticated animal.Known for its loyalty and faithfulness,it can be found as a welcome guest in many households across the world.`,
      customStyle:
        'background: #f7f7f7;border-radius: 4px;margin-bottom: 24px;border: 0;overflow: hidden'
    }
  },
  watch: {
    expertShow: function (value) {
      if (value) {
        this.getProduct(this.expertData.code)
      } else {
        this.expertProductList = []
      }
    }
  },
  methods: {
    getProduct (expertCode) {
      this.$get(`/cos/expert-product/list/${expertCode}`).then((r) => {
        this.expertProductList = r.data.data
        console.log(JSON.stringify(this.expertProductList))
      })
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
