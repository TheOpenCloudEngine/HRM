package org.opencloudengine.garuda.backend.clientjob;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

import java.util.concurrent.TimeUnit;

import static net.sf.expectit.matcher.Matchers.regexp;

/**
 * Created by uengine on 2016. 9. 2..
 */
public class ExpectTest {

    public static void main(String args[]) throws Exception{
//        Expect expect;
//        Process process = Runtime.getRuntime().exec("/bin/sh");
//
//        expect = new ExpectBuilder()
//                .withInputs(process.getInputStream())
//                .withOutput(process.getOutputStream())
//                .withTimeout(1, TimeUnit.SECONDS)
//                .withExceptionOnFailure()
//                .build();
//
//        String working = "/Users/uengine/test";
//        expect.sendLine("cat " + working + "/PID");
//        try {
//            String input = expect.expect(regexp("[0-9]+")).getInput().split("\n")[0];
//            expect.sendLine("ps -ef | grep " + input);
//            expect.expect(regexp(".+")).getInput();
//            expect.sendLine("cat " + working + "/CODE");
//            expect.expect(regexp("1")).getInput();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        expect.close();

        Process process = Runtime.getRuntime().exec("/bin/sh");

        Expect expect = new ExpectBuilder()
                .withInputs(process.getInputStream())
                .withOutput(process.getOutputStream())
                .withTimeout(1, TimeUnit.SECONDS)
                .withExceptionOnFailure()
                .build();
        // try-with-resources is omitted for simplicity
        expect.sendLine("ls -lh");
        // capture the total
        String total = expect.expect(regexp("^total (.*)")).group(1);
        System.out.println("Size: " + total);
        // capture file list
        String list = expect.expect(regexp("\n$")).getBefore();
        // print the result
        System.out.println("List: " + list);

        process.destroy();
        expect.close();
    }
}
