/*
 * Copyright 2018 tomoncle
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

package com.tomoncle.tools.test.ssh;

import com.jcabi.ssh.Shell;
import com.jcabi.ssh.SshByPassword;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 * @author tomoncle
 */
public class SshTest {

    @Test
    @SneakyThrows
    public void sshSayHello(){
        Shell shell = new SshByPassword("172.16.110.6", 22, "root", "123456");
        System.out.println(shell.toString());
        String stdout = new Shell.Plain(shell).exec("echo 'Hello, world!'");
        System.out.println(stdout);
    }
}
