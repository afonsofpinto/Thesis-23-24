<template>
  <main class="flex flex-col items-center gap-10">
    <img src="../assets/mbanco.png" alt="banco" width="300">
    <a-form
      :model="formState"
      name="login"
      autocomplete="off"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item
        label="Name"
        name="name"
        :rules="[{ required: true, message: 'Please input your name!' }]"
      >
        <a-input v-model:value="formState.name">
          <template #prefix>
            <UserOutlined class="site-form-item-icon" />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item
        label="Pin"
        name="pin"
        :rules="[{ required: true, message: 'Please input your pin!' }]"
      >
        <a-input-password v-model:value="formState.pin">
          <template #prefix>
            <LockOutlined class="site-form-item-icon" />
          </template>
        </a-input-password>
      </a-form-item>

      <a-form-item class="place-items-end">
        <a-button :disabled="disabled" @click="onClick" html-type="submit">Log in</a-button>
      </a-form-item>
    </a-form>
  </main>
</template>

<script lang="ts" setup>
import router from '../router'
import { reactive, computed } from 'vue';
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
interface FormState {
  name: string,
  pin: string,
}

const formState = reactive<FormState>({
  name:"",
  pin: ""
});
const onFinish = (values: any) => {
  console.log('Success:', values);
};

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};

const disabled = computed(() => {
  return !formState.pin || formState.pin != "1234";
});

const onClick = () => {
  router.push({ path: '/homepage', query: { value: formState.name }});
}
</script>

