package com.exam.developer.business;

import com.exam.developer.business.interfaces.Executor;
import com.exam.developer.model.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.exam.developer.catalogs.CommandsEnum.*;

@Slf4j
@Service
public class ExecutorImpl implements Executor {
    private List<List<Node>> nodes = new ArrayList<>();
    private HashSet<String> software = new HashSet<>();

    @Override
    public List<String> processor(List<String> commands) {
        log.info("Executing command processor");
        List<String> results = new ArrayList<>();

        for (String command : commands) {

            String[] splited = command.split("\\s+");

            if (splited[0].equals(DEPEND.command())) {
                results.addAll(this.depend(command));

            } else if (splited[0].equals(INSTALL.command())) {
                results.addAll(this.install(command));

            } else if (splited[0].equals(REMOVE.command())) {
                results.addAll(this.remove(command));

            } else if (splited[0].equals(LIST.command())) {
                results.addAll(this.list(command));

            } else if (splited[0].equals(END.command())) {
                results.add(this.end(command));
                break;
            }
        }

        return results;
    }


    @Override
    public List<String> depend(String command) {
        log.info("Processing DEPENDD command");
        List<String> results = new ArrayList<>();
        String[] splited = command.split("\\s+");

        List<Node> nodeList = new ArrayList<>();


        for (int i = 1; i < splited.length; i++) {
            Node node = new Node(splited[i], false);
            nodeList.add(node);
        }

        nodes.add(nodeList);
        results.add(command);

        return results;
    }

    @Override
    public List<String> install(String command) {
        log.info("Processing INSTALL command");
        List<String> results = new ArrayList<>();
        List<String> items = new LinkedList<>(Arrays.asList(command.split("\\s+")));
        items.remove(0);
        HashSet<String> tmp = new HashSet<>();
        results.add(command);

        if (!items.isEmpty()) {
            if (!software.contains(items.get(0))) {
                for (int nodeNumber = 0; nodeNumber < nodes.size(); nodeNumber++) {
                    for (int itemPosition = 0; itemPosition < items.size(); itemPosition++) {
                        for (int nodeIndex = 0; nodeIndex < nodes.get(nodeNumber).size(); nodeIndex++) {
                            if (items.get(itemPosition).equals(nodes.get(nodeNumber).get(nodeIndex).getName())) {
                                nodes.get(nodeNumber).get(nodeIndex).setTransitory(true);
                                software.add(nodes.get(nodeNumber).get(nodeIndex).getName());

                                for (int lastIndex = nodeIndex + 1; lastIndex < nodes.get(nodeNumber).size(); lastIndex++) {
                                    String dependency = nodes.get(nodeNumber).get(lastIndex).getName();

                                    if (!software.contains(dependency)) {
                                        software.add(dependency);
                                        tmp.add(dependency);
                                    }
                                }
                            }
                        }
                    }
                }

                for (String s : tmp) {
                    results.add("   Installing " + s);
                }

                results.add("   Installing " + items.get(items.size() - 1));
                software.add(items.get(items.size() - 1));
            } else {
                results.add("   " + items.get(0) + " is already installed.");
            }
        }

        return results;
    }

    @Override
    public List<String> remove(String command) {
        log.info("Processing REMOVE command");
        List<String> results = new ArrayList<>();
        String[] splited = command.split("\\s+");
        results.add(command);

        return results;
    }

    @Override
    public List<String> list(String command) {
        log.info("Processing LIST command");
        List<String> results = new ArrayList<>();
        results.add(command);

        for (String s : software) {
            results.add("   " + s);
        }

        return results;
    }

    @Override
    public String end(String command) {
        log.info("Processing END command");
        return command;
    }
}
