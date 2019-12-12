# 定制化View

**Taost 超高使用频率，最大优势是可以实现跨界面展示，google希望snackbar代替它，好像没成功。
Toast的展示本质是通过binder跨进程调用 NotificationManager对它进行管理，所以受限与通知权限**

主要功能：
   1. 兼容通知权限关闭；

#### 使用前提：
1. 需要实现一个接口；
2. 并且出事化

```java
        public interface WApp {//需要实现的接口
        
            Activity getTaskTop();
        
            Context getContext();
        }

        public class MApp extends Application implements WApp {
        
            private static MApp mApp;
        
            /**
             * activity 队列
             */
            private List<SoftReference<Activity>> mTask;
        
            @Override
            public void onCreate() {
                super.onCreate();
        
                this.mApp = this;
                mTask = new ArrayList<>();
                WToast.init(this); //初始化
            }

```

#### 代码动态设置如下

```java
           WToast.show("hello world");
```

## 待完善！
