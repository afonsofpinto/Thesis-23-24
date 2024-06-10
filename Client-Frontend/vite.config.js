import { fileURLToPath, URL } from 'node:url';

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueJsx from '@vitejs/plugin-vue-jsx';
import Components from 'unplugin-vue-components/vite';
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers';

// Vite configuration for Vue projects
export default defineConfig({
  plugins: [
    vue(),  // Enable support for Vue 3
    vueJsx(),  // Support for JSX in Vue components
    Components({
      resolvers: [
        AntDesignVueResolver({
          importStyle: 'less',  // Import Ant Design styles using less
        })
      ]
    })
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))  // Setup an alias for the src directory
    }
  },
});

