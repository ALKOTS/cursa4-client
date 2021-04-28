//import org.json.JSONObject;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class ss {
//        public void acceptQuestions() throws Exception {
//                acceptUpdater(question_to_delete, questions_list, 2, changed1);
//
//                AtomicInteger asyncChecker= new AtomicInteger();
//
//                for(Map.Entry me:questions_list.entrySet()){
//
//                        String finalI1 = me.getKey().toString();
//                        new Thread(()->{
//                                String finalI = finalI1;
//                                try {
//                                        JSONObject toPut=new JSONObject(){{
//                                                put("question",questions_list.get(finalI).get(0));
//                                                put("answer", questions_list.get(finalI).get(1));
//                                        }};
//                                        Unirests.put(questions_list.get(finalI1).get(2),toPut);
//                                } catch (Exception e) {
//                                }
//
//                                asyncChecker.getAndIncrement();
//                        }).start();
//                }
//
//                AtomicInteger asyncChecker2= new AtomicInteger();
//
//                for(int i=0; i<added_questions.size(); i++){
//                        int finalI = i;
//                        new Thread(()->{
//                                try {
//
//                                        JSONObject toPost=new JSONObject(){{
//                                                put("question",added_questions.get(finalI).get(0));
//                                                put("answer", added_questions.get(finalI).get(1));
//                                        }};
//
//                                        Unirests.post(Main.dbLink+"/questions", toPost);
//
//                                } catch (Exception e) {
//                                        System.out.println("Хоба");
//                                }
//                                asyncChecker2.getAndIncrement();
//                        }).start();
//                }
//
//                while (asyncChecker.get()<questions_list.size() || asyncChecker2.get() <added_questions.size()){
//                        Thread.sleep(100);
//                }
//                Main.get_questions();
//
//                changed1.clear();
//
//        }
//}