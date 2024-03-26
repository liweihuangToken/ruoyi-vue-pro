<template>
	<view>
		<view class="top" v-if="topShow" @click="goTop()">
			<uni-icons type="arrow-up" :size="30"></uni-icons>
		</view>
	</view>
</template>
 
<script>
	export default {
		name:"viewTop",
		data() {
			return {
				topShow: false,
			};
		},
		props: {
			scrollTop: {
				type: Number,
				default: 0
			},
		},
		
		methods:{
			topData(e){
                 //这个e直接是滚动时候的高度
				this.topShow = e>200
			},
			goTop(){
				uni.pageScrollTo({
					scrollTop: this.scrollTop,
					duration: 300
				});
			},
		}
	}
</script>
 
<style lang="scss">
	.top {
		height: 80rpx;
		width: 80rpx;
		text-align: center;
		line-height: 80rpx;
		border-radius: 50%;
		background-color: rgba($color: #FFFFFF, $alpha: 0.8);
		border: 1rpx solid #ddd;
		position: fixed;
		z-index: 999;
		// right: 30rpx;
		left: 45%;
		bottom: 110rpx;
		animation: heart 0.3s;
		@keyframes heart {
			0% {
				right: -30rpx;
			}
			100% {
				right: 30rpx;
			}
		}
	}
</style>