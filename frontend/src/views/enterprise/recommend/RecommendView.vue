<template>
  <a-modal v-model="show" title="求职者推荐详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <div v-if="recommendData.status == 1">
        <a-button key="audit" @click="audit(2)">
          通过
        </a-button>
        <a-button key="audit" @click="audit(3)" type="danger">
          驳回
        </a-button>
      </div>
      <a-button key="back" @click="onClose" type="danger" v-else>
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="recommendData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">求职者信息</span></a-col>
        <a-col :span="8"><b>求职者姓名：</b>
          {{ recommendData.name }}
        </a-col>
        <a-col :span="8"><b>民族：</b>
          {{ recommendData.nationality }}
        </a-col>
        <a-col :span="8"><b>性别：</b>
          {{ recommendData.sex }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>政治面貌：</b>
          {{ recommendData.politicalStatus }}
        </a-col>
        <a-col :span="8"><b>籍贯：</b>
          {{ recommendData.nativePlace }}
        </a-col>
        <a-col :span="8"><b>工作单位：</b>
          {{ recommendData.employer }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>职务：</b>
          {{ recommendData.position }}
        </a-col>
        <a-col :span="16"><b>通讯地址：</b>
          {{ recommendData.address }}
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>手机号：</b>
          {{ recommendData.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>专业方向一级：</b>
          {{ recommendData.levelOne }}
        </a-col>
        <br/>
        <br/>
        <a-col :span="8"><b>出生日期：</b>
          {{ recommendData.birthDate }}
        </a-col>
        <a-col :span="16"><b>专业方向二级：</b>
          {{ recommendData.levelTwo }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>职称：</b>
          {{ recommendData.jobTitle }}
        </a-col>
        <a-col :span="8"><b>特殊称谓：</b>
          {{ recommendData.specialAppellation }}
        </a-col>
        <a-col :span="8"><b>邮箱：</b>
          {{ recommendData.mail }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>固定电话：</b>
          {{ recommendData.fixedTelephone }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ recommendData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>个人简介：</b>
          {{ recommendData.profile }}
        </a-col>
      </a-row>
      <br/>
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
  name: 'recommendView',
  props: {
    recommendShow: {
      type: Boolean,
      default: false
    },
    recommendData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.recommendShow
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
    recommendShow: function (value) {
      if (value) {
        if (this.recommendData.images !== null && this.recommendData.images !== '') {
          this.imagesInit(this.recommendData.images)
        }
      }
    }
  },
  methods: {
    audit (status) {
      this.$get(`/cos/expert-recommend/audit`, {
        status,
        auditId: this.recommendData.id
      }).then((r) => {
        this.$emit('success')
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
