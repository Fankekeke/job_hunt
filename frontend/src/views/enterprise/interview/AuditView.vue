<template>
  <a-modal v-model="show" title="面试详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="close" @click="onClose" type="danger" v-if="pluralismData.status == 5 || pluralismData.status == 3">
        关闭
      </a-button>
      <a-button key="result" @click="onCheck(5)" v-if="pluralismData.status == 4">
        面试结束
      </a-button>
      <a-button key="check" @click="onCheck(4)" v-if="pluralismData.status == 1 || pluralismData == 2">
        邀约面试
      </a-button>
      <a-button key="back" @click="onCheck(3)" type="danger" v-if="pluralismData.status == 1 || pluralismData == 2">
        驳回
      </a-button>
    </template>
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
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <br/>
        <a-col :span="8"><b>出生日期：</b>
          {{ expertData.birthDate }}
        </a-col>
        <a-col :span="16"><b>专业方向二级：</b>
          {{ expertData.levelTwo }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>职称：</b>
          {{ expertData.jobTitle }}
        </a-col>
        <a-col :span="8"><b>特殊称谓：</b>
          {{ expertData.specialAppellation }}
        </a-col>
        <a-col :span="8"><b>邮箱：</b>
          {{ expertData.mail }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>固定电话：</b>
          {{ expertData.fixedTelephone }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ expertData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="24"><b>个人简介：</b>
          {{ expertData.profile }}
        </a-col>
      </a-row>
      <br/>
    </div>
    <div style="font-size: 13px;font-family: SimHei" v-if="pluralismInfo">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">兼职信息</span></a-col>
        <a-col :span="8"><b>兼职标题：</b>
          {{ pluralismInfo.title }}
        </a-col>
        <a-col :span="8"><b>岗位名称：</b>
          {{ pluralismInfo.postName }}
        </a-col>
        <a-col :span="8"><b>结算方式：</b>
          <span v-if="pluralismInfo.paymentMethod == 1">日结</span>
          <span v-if="pluralismInfo.paymentMethod == 2">周结</span>
          <span v-if="pluralismInfo.paymentMethod == 3">月结</span>
          <span v-if="pluralismInfo.paymentMethod == 4">季结</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>学历要求：</b>
          <span v-if="pluralismInfo.academic == 1">小学</span>
          <span v-if="pluralismInfo.academic == 2">初中</span>
          <span v-if="pluralismInfo.academic == 3">高中</span>
          <span v-if="pluralismInfo.academic == 4">专科</span>
          <span v-if="pluralismInfo.academic == 5">本科</span>
          <span v-if="pluralismInfo.academic == 6">研究生</span>
        </a-col>
        <a-col :span="8"><b>工作时间：</b>
          {{ pluralismInfo.workTime }}
        </a-col>
        <a-col :span="8"><b>工作时段：</b>
          {{ pluralismInfo.workHour }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>工作地点：</b>
          {{ pluralismInfo.workAddress }}
        </a-col>
        <a-col :span="16"><b>所属行业：</b>
          {{ pluralismInfo.industryName }}
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>岗位描述：</b>
          {{ pluralismInfo.describe }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">工作要求</span></a-col>
        <a-col :span="24">{{ pluralismInfo.workRequire }}</a-col>
        <br/>
      </a-row>
      <br/>
    </div>
    <div style="font-size: 13px;font-family: SimHei" v-if="postInfo">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">岗位信息</span></a-col>
        <a-col :span="8"><b>岗位编号：</b>
          {{ postInfo.code }}
        </a-col>
        <a-col :span="8"><b>岗位名称：</b>
          {{ postInfo.postName }}
        </a-col>
        <a-col :span="8"><b>学历要求：</b>
          <span v-if="postInfo.academic == 1">小学</span>
          <span v-if="postInfo.academic == 2">初中</span>
          <span v-if="postInfo.academic == 3">高中</span>
          <span v-if="postInfo.academic == 4">专科</span>
          <span v-if="postInfo.academic == 5">本科</span>
          <span v-if="postInfo.academic == 6">研究生</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">所需经验</span></a-col>
        {{ postInfo.experience }}
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">岗位职责</span></a-col>
        {{ postInfo.responsibility }}
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>福利：</b>
          {{ postInfo.welfare }}
        </a-col>
        <a-col :span="8"><b>工作时间：</b>
          {{ postInfo.workTime }}
        </a-col>
        <a-col :span="8"><b>工作时段：</b>
          {{ postInfo.workHour }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>工作地点：</b>
          {{ postInfo.address }}
        </a-col>
        <a-col :span="16"><b>所属行业：</b>
          {{ postInfo.industryName }}
        </a-col>
        <br/>
        <br/>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">工作要求</span></a-col>
        <a-col :span="24">{{ postInfo.workRequire }}</a-col>
        <br/>
      </a-row>
      <br/>
      <br/>
    </div>
    <div style="font-size: 13px;font-family: SimHei">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">面试时间</span></a-col>
        <a-date-picker v-model="interDate" />
      </a-row>
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
  name: 'AuditView',
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
      previewImage: '',
      expertData: null,
      postInfo: null,
      pluralismInfo: null,
      interDate: null
    }
  },
  watch: {
    pluralismShow: function (value) {
      if (value) {
        this.interDate = null
        this.getInterViewDetail(this.pluralismData.id)
      }
    }
  },
  methods: {
    onCheck (status) {
      var data = Object.assign({}, this.pluralismData)
      if (status === 4) {
        if (!this.interDate) {
          this.$message.error('请选择时间！')
          return false
        } else {
          var date = moment(this.interDate).format('YYYY-MM-DD')
          data.createDate = date
        }
      }
      data.status = status
      this.$post(`/cos/interview-info/audit`, {...data}).then((r) => {
        this.$emit('success')
      })
    },
    getInterViewDetail (id) {
      this.$get(`/cos/interview-info/detail/${id}`).then((r) => {
        this.expertData = r.data.staff
        this.pluralismInfo = r.data.pluralism
        this.postInfo = r.data.post
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
