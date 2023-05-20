<template>
  <div>
    <div ref="vantaRef" style="width:100%;height:100vh"></div>
<!--    <div class="my_title">Hello,World</div>-->
  </div>

</template>

<script>
import * as THREE from 'three'
import GLOBE from 'vanta/src/vanta.globe'
export default {
  name: "background",
  data() {
    return {

    };
  },
  mounted() {
    this.vantaEffect = GLOBE({
      el: this.$refs.vantaRef,
      THREE: THREE
    })
    VANTA.GLOBE({
      el: this.$refs.vantaRef,
      mouseControls: true,
      touchControls: true,
      gyroControls: false,
      minHeight: 200.00,
      minWidth: 200.00,
      scale: 1.00,
      scaleMobile: 1.00,
      backgroundColor: 0xffffff
    })


  },
  beforeDestroy() {
    if (this.vantaEffect) {
      this.vantaEffect.destroy()
    }
  },
  methods: {

  },

};
</script>

