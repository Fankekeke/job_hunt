<template>
  <a-modal v-model="show" title="岗位详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="pluralismData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">岗位信息</span></a-col>
        <a-col :span="8"><b>岗位编号：</b>
          {{ pluralismData.code }}
        </a-col>
        <a-col :span="8"><b>岗位名称：</b>
          {{ pluralismData.postName }}
        </a-col>
        <a-col :span="8"><b>学历要求：</b>
          <span v-if="pluralismData.academic == 1">小学</span>
          <span v-if="pluralismData.academic == 2">初中</span>
          <span v-if="pluralismData.academic == 3">高中</span>
          <span v-if="pluralismData.academic == 4">专科</span>
          <span v-if="pluralismData.academic == 5">本科</span>
          <span v-if="pluralismData.academic == 6">研究生</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">所需经验</span></a-col>
        {{ pluralismData.experience }}
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">岗位职责</span></a-col>
        {{ pluralismData.responsibility }}
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>福利：</b>
          {{ pluralismData.welfare }}
        </a-col>
        <a-col :span="8"><b>工作时间：</b>
          {{ pluralismData.workTime }}
        </a-col>
        <a-col :span="8"><b>工作时段：</b>
          {{ pluralismData.workHour }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>工作地点：</b>
          {{ pluralismData.address }}
        </a-col>
        <a-col :span="16"><b>所属行业：</b>
          {{ pluralismData.industryName }}
        </a-col>
        <br/>
        <br/>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">工作要求</span></a-col>
        <a-col :span="24">{{ pluralismData.workRequire }}</a-col>
        <br/>
        <br/>
        <br/>
        <a-col :span="8"><b>创建时间：</b>
          {{ pluralismData.createDate }}
        </a-col>
        <a-col :span="16"><b>上下架：</b>
          <span v-if="pluralismData.delFlag == 0" style="color: red">下架</span>
          <span v-if="pluralismData.delFlag == 1" style="color: green">上架</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>企业名称：</b>
          {{ pluralismData.enterpriseName }}
        </a-col>
        <a-col :span="8"><b>企业性质：</b>
          {{ pluralismData.nature }}
        </a-col>
      </a-row>
      <br/>
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
  name: 'postView',
  props: {
    pluralismShow: {
      type: Boolean,
      default: false
    },
    pluralismData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.pluralismShow
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
      previewImage: ''
    }
  },
  watch: {
    pluralismShow: function (value) {
      if (value) {
        if (this.pluralismData.images !== null && this.pluralismData.images !== '') {
          this.imagesInit(this.pluralismData.images)
        }
      }
    }
  },
  methods: {
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
