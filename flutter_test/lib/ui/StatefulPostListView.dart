import 'dart:math';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:test1/ui/PostViewModel.dart';

class StatefulPostListView extends StatefulWidget {
  const StatefulPostListView({Key? key}) : super(key: key);

  @override
  _PostListViewState createState() => _PostListViewState();
}

class _PostListViewState extends State<StatefulPostListView> {

  var listOfEmojis = ['😸','😽','😼','🙀','😻'];

  var counter = 0;
  void rollTheDice() {
    counter = Random().nextInt(listOfEmojis.length);
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<PostViewModel>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Random Facts About Cat'),
      ),
      body: Column(
        children: [
          ElevatedButton(
            onPressed: () {
              // setState(() {
                rollTheDice();
                viewModel.fetchPosts();
              // });
            },
            style: ElevatedButton.styleFrom(
              textStyle: const TextStyle(fontSize: 60, color: Color(0x2b2b2b)),
              elevation: 0,
              backgroundColor: const Color(0x00000000),
            ),
            child: Text(listOfEmojis[counter]),
          ),
          if (viewModel.isLoading)
            Expanded(
              child: Stack(
                children: [
                  ListView.builder(
                    itemCount: viewModel.posts.length,
                    itemBuilder: (context, index) {
                      final post = viewModel.posts[index];
                      return ListTile(
                        title: Text(post.content),
                      );
                    },
                  ),
                  const Center(
                    child: CircularProgressIndicator(),
                  ),
                ],
              ),
            )
          else
            Expanded(
              child: ListView.builder(
                itemCount: viewModel.posts.length,
                itemBuilder: (context, index) {
                  final post = viewModel.posts[index];
                  return ListTile(
                    title: Text(post.content),
                  );
                },
              ),
            ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          viewModel.clearPost();
        },
        child: const Icon(Icons.delete),
      ),
    );
  }
}
