package agh.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class RandomData {
    private List<ApiData> data;
    public void read(final String path) throws IOException {
        final String json = new String(Files.readAllBytes(Paths.get(path)));
        final Type collectionType = new TypeToken<List<ApiData>>() {}.getType();
        data = new Gson().fromJson(json, collectionType);
    }

    public List<String> getConferenceNames() {
        final List<String> parts1 = data.stream()
                .map(apiData -> apiData.conf_name1)
                .collect(Collectors.toList());
        final List<String> parts2 = data.stream()
                .map(apiData -> apiData.conf_name2)
                .collect(Collectors.toList());
        Collections.shuffle(parts1);
        Collections.shuffle(parts2);
        return IntStream.range(0, parts1.size())
                .mapToObj(i -> parts1.get(i) + ' ' + parts2.get(i))
                .collect(Collectors.toList());
    }
    public List<String> getWorkshopNames() {
        final List<String> parts1 = data.stream()
                .map(apiData -> apiData.conf_name3)
                .collect(Collectors.toList());
        final List<String> parts2 = data.stream()
                .map(apiData -> apiData.conf_name2)
                .collect(Collectors.toList());
        Collections.shuffle(parts1);
        Collections.shuffle(parts2);
        return IntStream.range(0, parts1.size())
                .mapToObj(i -> parts1.get(i) + ' ' + parts2.get(i))
                .collect(Collectors.toList());
    }
    public List<String> getNames() {
        final List<String> firstnames = data.stream()
                .map(apiData -> apiData.first_name)
                .collect(Collectors.toList());
        final List<String> lastnames = data.stream()
                .map(apiData -> apiData.last_name)
                .collect(Collectors.toList());
        Collections.shuffle(firstnames);
        Collections.shuffle(lastnames);
        return IntStream.range(0, firstnames.size())
                .mapToObj(i -> firstnames.get(i) + ' ' + lastnames.get(i))
                .collect(Collectors.toList());
    }

    public List<String> getStudentCards() {
        return data.stream()
                .map(apiData -> apiData.student_card)
                .collect(Collectors.toList());
    }

    public List<String> getCompanyNames() {
        return data.stream()
                .map(apiData -> apiData.company_name)
                .collect(Collectors.toList());
    }

    public List<String> getEmails() {
        return data.stream()
                .map(apiData -> apiData.email)
                .collect(Collectors.toList());
    }

    public List<String> getLogins() {
        return data.stream()
                .map(apiData -> apiData.login1)
                .collect(Collectors.toList());
    }
    public List<String> getPhones() {
        return data.stream()
                .map(apiData -> apiData.phone)
                .collect(Collectors.toList());
    }
    public List<String> getPasswords() {
        return data.stream()
                .map(Object::hashCode)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public LocalTime getRandomDayTime() {
        LocalTime time = LocalTime.of(10, 50);
        return LocalTime.of(ThreadLocalRandom.current().nextInt(8,21),
                ThreadLocalRandom.current().nextInt(0,60));
    }
}