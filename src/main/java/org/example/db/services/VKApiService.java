package org.example.db.services;

import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VKApiService {
    private final StudentService studentService;

    @Autowired
    public VKApiService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void getAdditionalInfoAndSaveStudents(Integer appId, String accessToken, String clientSecret, String groupId) {
        var vk = new VkApiClient(HttpTransportClient.getInstance());
        var actor = new ServiceActor(appId, clientSecret, accessToken);

        try {
            var userIds = vk.groups().getMembers(actor).groupId(groupId).execute().getItems();
            var cnt = 0;
            System.out.println("Обработано " + cnt + " из " + userIds.size());
            for (var userId : userIds) {
                processUserAndSaveFollowers(actor, userId);
                cnt++;
                if (cnt % 50 == 0) System.out.println("Обработано " + cnt + " из " + userIds.size());
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processUserAndSaveFollowers(ServiceActor actor, long userId) throws ApiException, ClientException {
        var vk = new VkApiClient(HttpTransportClient.getInstance());
        var users = vk.users().get(actor).userIds(String.valueOf(userId)).fields(Fields.COUNTERS).lang(Lang.RU).execute();

        for (var user : users) {
            processUserCountersAndSaveFollowers(user);
        }
    }

    private void processUserCountersAndSaveFollowers(GetResponse user) {
        var counters = user.getCounters();
        if (counters == null) return;

        var followersCnt = counters.getFollowers();
        if (followersCnt == null) return;

        var students = studentService.getStudentByName(user.getFirstName(), user.getLastName());

        for (var student : students) {
            student.setFollowersCnt(followersCnt);
            studentService.saveStudent(student);
        }
    }
}