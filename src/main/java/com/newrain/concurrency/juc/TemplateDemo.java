package com.newrain.concurrency.juc;

/**
 * AQS 模板方法
 */
public class TemplateDemo {


    static abstract class AbstractAction {

        public void tempMethod() {
            System.out.println("模板方法的算法骨架被执行");
            beforeAction();
            action();
            afterAction();
        }

        // 执行前
        protected void beforeAction() {
            System.out.println("准备执行钩子方法");
        }

        // 钩子方法 这里定义为一个抽象方法
        public abstract void action();

        // 执行后
        protected void afterAction() {
            System.out.println("钩子方法执行完成");
        }

    }


    // 子类A 提供钩子方法实现
    static class ActionA extends AbstractAction {

        @Override
        public void action() {
            System.out.println("钩子方法的实现 ActionA.action() 被执行");
        }
    }

    // 子类B 提供钩子方法实现
    static class ActionB extends AbstractAction {

        @Override
        public void action() {
            System.out.println("钩子方法的实现 ActionA.action() 被执行");
        }
    }

    public static void main(String[] args) {
        AbstractAction action;

        action = new ActionA();
        action.tempMethod();

/*        action = new ActionB();
        action.tempMethod();*/
    }
}