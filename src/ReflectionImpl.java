import reflection.api.Investigator;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class ReflectionImpl implements Investigator {

    private Class instanceClass;
    private Object savedInstanceOfSomething;

    @Override
    public void load(Object anInstanceOfSomething) {
        instanceClass = anInstanceOfSomething.getClass();
        savedInstanceOfSomething= anInstanceOfSomething;
    }

    @Override
    public int getTotalNumberOfMethods() {
        return (instanceClass.getDeclaredMethods().length);
    }

    @Override
    public int getTotalNumberOfConstructors() {
       return (instanceClass.getConstructors().length);
    }

    @Override
    public int getTotalNumberOfFields() {
        return (instanceClass.getDeclaredFields().length);
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {

        Class[] allInterfaces = instanceClass.getInterfaces();
        Set<String> res = new HashSet<String>();

        for (Class curInterface : allInterfaces)
        {
            if (curInterface.isInterface())
            {
                res.add(curInterface.getSimpleName());
            }
        }
        return res;
    }

    @Override
    public int getCountOfConstantFields() {
        int counter=0;
        int curModifier;
        for (Field curFields : instanceClass.getDeclaredFields()) {
            curModifier = curFields.getModifiers();
            if (Modifier.isFinal(curModifier))
                counter++;
        }
        return counter;
    }

    @Override
    public int getCountOfStaticMethods() {
        int counter=0;
        int curModifier;
        for (Method curMethods : instanceClass.getDeclaredMethods()) {
            curModifier = curMethods.getModifiers();
            if (Modifier.isStatic(curModifier))
                counter++;
        }
        return counter;
    }

    @Override
    public boolean isExtending() {
        return (instanceClass.getSuperclass().getSimpleName() != "Object");
    }

    @Override
    public String getParentClassSimpleName() {
        return (instanceClass.getSuperclass().getSimpleName());
    }

    @Override
    public boolean isParentClassAbstract() {
        return (Modifier.isAbstract(instanceClass.getSuperclass().getModifiers()));
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        Set<String> res= new HashSet<String>();
        Class curSuperClass = instanceClass;

        while (curSuperClass != null)
        {
            for (Field curField : curSuperClass.getDeclaredFields())
                res.add(curField.getName());
            curSuperClass = curSuperClass.getSuperclass();
        }
        return res;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {

        int i= 0, res = 0;
        Class [] SendingForMethod = new Class [args.length];

        for (Object curObh : args) {
            SendingForMethod[i] = args[i].getClass();
            i++;
        }

        try {
            Method func = instanceClass.getMethod(methodName, SendingForMethod);
            res = (int) func.invoke(savedInstanceOfSomething,args);
        } catch (Exception e) {}

        return res;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {

        Object obj=null;

        try{
        if (numberOfArgs == 0)
        {
            obj = instanceClass.newInstance();
        }
        else {

            for (Constructor curCtor : instanceClass.getConstructors()) {
                if (curCtor.getParameterCount() == numberOfArgs)
                    obj=curCtor.newInstance(args);
            }
        }} catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {

        Object obj = null;

        try {//he is getting the good method!
            Method func = instanceClass.getDeclaredMethod(name,parametersTypes);
            func.setAccessible(true);
            obj = func.invoke(savedInstanceOfSomething,args);
        } catch (Exception e) {}

        return obj;
    }

    @Override
    public String getInheritanceChain(String delimiter) {

        String res = new String();
        LinkedList<Class> InheritanceList = new LinkedList<Class>();
        Class curSuperClass = instanceClass.getSuperclass();
        while (curSuperClass != null)
        {
            InheritanceList.addFirst(curSuperClass);
            curSuperClass = curSuperClass.getSuperclass();
        }

        for (Class curClass : InheritanceList) {
            res+=curClass.getSimpleName()+delimiter;
        }
        res+=instanceClass.getSimpleName();
        return res;
    }
}