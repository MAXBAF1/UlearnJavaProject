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

import java.util.Objects;
import java.util.stream.Collectors;

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
            var userIds = vk.groups().getMembers(actor).groupId(groupId).execute().getItems()
                    .stream().map(Objects::toString).collect(Collectors.toList());
            var users = vk.users().get(actor).userIds(userIds)
                    .fields(Fields.FOLLOWERS_COUNT, Fields.SEX)
                    .lang(Lang.RU).execute();

            for (var user : users) {
                findStudentAndSaveFollowers(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findStudentAndSaveFollowers(GetResponse user) {
        var students = studentService.getStudentByName(user.getFirstName(), user.getLastName());

        for (var student : students) {
            student.setSex(user.getSex());
            student.setFollowersCnt(user.getFollowersCount());
            studentService.saveStudent(student);
        }
    }
}