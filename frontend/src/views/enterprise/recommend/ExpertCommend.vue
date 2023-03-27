<template>
  <a-modal v-model="show" title="填写推荐求职者信息" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='求职者姓名' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入求职者姓名!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='民族' v-bind="formItemLayout">
            <a-input v-decorator="[
            'nationality'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='性别' v-bind="formItemLayout">
            <a-input v-decorator="[
            'sex'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='政治面貌' v-bind="formItemLayout">
            <a-input v-decorator="[
            'politicalStatus'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='籍贯' v-bind="formItemLayout">
            <a-input v-decorator="[
            'nativePlace'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='工作单位' v-bind="formItemLayout">
            <a-input v-decorator="[
            'employer'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='职务' v-bind="formItemLayout">
            <a-input v-decorator="[
            'position'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='通讯地址' v-bind="formItemLayout">
            <a-input v-decorator="[
            'address'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='手机号' v-bind="formItemLayout">
            <a-input v-decorator="[
            'phone'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='专业方向一级' v-bind="formItemLayout">
            <a-input v-decorator="[
            'levelOne'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='专业方向二级' v-bind="formItemLayout">
            <a-input v-decorator="[
            'levelTwo'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='出生日期' v-bind="formItemLayout">
            <a-input v-decorator="[
            'birthDate'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='职称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'jobTitle'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='特殊称谓' v-bind="formItemLayout">
            <a-input v-decorator="[
            'specialAppellation'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='邮箱' v-bind="formItemLayout">
            <a-input v-decorator="[
            'mail'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='固定电话' v-bind="formItemLayout">
            <a-input v-decorator="[
            'fixedTelephone'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='个人简介' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'profile',
             { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'ExpertAdd',
  props: {
    expertVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.expertVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          values.userId = this.currentUser.userId
          values.code = this.currentUser.userCode
          this.$post('/cos/expert-recommend', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
